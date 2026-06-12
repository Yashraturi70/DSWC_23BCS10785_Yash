// SELECT s.student_id, s.full_name
// FROM students s
// LEFT JOIN course_registrations cr ON s.student_id = cr.student_id
// WHERE cr.student_id IS NULL;

// CREATE INDEX idx_course_registrations_student_id ON course_registrations (student_id);


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

interface RegistrationManager {
    void enrollAtRiskStudents();
}

abstract class DatabaseConnectionProvider {
    private final String url = "jdbc:postgresql://localhost:3000/edixo";
    private final String user = "postgres";
    private final String password = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

class EdixoRegistrationRepository extends DatabaseConnectionProvider implements RegistrationManager {
    @Override
    public void enrollAtRiskStudents() {
        String selectSql = "SELECT s.student_id, s.full_name " +
                           "FROM students s " +
                           "LEFT JOIN course_registrations cr ON s.student_id = cr.student_id " +
                           "WHERE cr.student_id IS NULL";
        
        String insertSql = "INSERT INTO course_registrations (student_id, course_code, semester) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             ResultSet rs = selectStmt.executeQuery()) {

            int count = 0;
            while (rs.next()) {
                long studentId = rs.getLong("student_id");
                
                insertStmt.setLong(1, studentId);
                insertStmt.setString(2, "Orientation 101");
                insertStmt.setString(3, "Fall 2026");
                insertStmt.addBatch();
                
                count++;
                if (count % 1000 == 0) {
                    insertStmt.executeBatch();
                }
            }
            if (count % 1000 != 0) {
                insertStmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}