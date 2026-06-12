// SELECT s.shipment_id, c.company_name, s.dispatch_date
// FROM shipments s
// INNER JOIN couriers c ON s.courier_id = c.courier_id
// WHERE s.status = 'DELAYED'
// ORDER BY s.dispatch_date DESC;

// CREATE INDEX idx_shipments_status_dispatch_desc ON shipments (status, dispatch_date DESC);


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

interface ReportGenerator {
    void printDelayedReport();
}

abstract class DatabaseRepository {
    private final String url = "jdbc:postgresql://localhost:3000/cargologix";
    private final String user = "postgres";
    private final String password = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

class LogisticsRepository extends DatabaseRepository implements ReportGenerator {
    @Override
    public void printDelayedReport() {
        String sql = "SELECT s.shipment_id, c.company_name, s.dispatch_date " +
                     "FROM shipments s " +
                     "INNER JOIN couriers c ON s.courier_id = c.courier_id " +
                     "WHERE s.status = ? " +
                     "ORDER BY s.dispatch_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "DELAYED");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    long shipmentId = rs.getLong("shipment_id");
                    String companyName = rs.getString("company_name");
                    java.sql.Timestamp dispatchDate = rs.getTimestamp("dispatch_date");
                    System.out.println(shipmentId + " | " + companyName + " | " + dispatchDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}