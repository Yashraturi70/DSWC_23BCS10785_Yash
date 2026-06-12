// SELECT h.asset_class, SUM(h.current_value) AS total_value
// FROM holdings h
// INNER JOIN investors i ON h.investor_id = i.investor_id
// WHERE h.investor_id = ?
// GROUP BY h.asset_class;

// CREATE INDEX idx_holdings_investor_asset_val ON holdings (investor_id, asset_class, current_value);


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

interface PortfolioManager {
    void restructurePortfolio(long investorId);
}

abstract class FinancialDatabaseConfig {
    private final String url = "jdbc:postgresql://localhost:3000/fire_db";
    private final String user = "postgres";
    private final String password = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

class PortfolioRepository extends FinancialDatabaseConfig implements PortfolioManager {
    @Override
    public void restructurePortfolio(long investorId) {
        String selectSql = "SELECT h.asset_class, SUM(h.current_value) AS total_value " +
                           "FROM holdings h " +
                           "INNER JOIN investors i ON h.investor_id = i.investor_id " +
                           "WHERE h.investor_id = ? " +
                           "GROUP BY h.asset_class";
        
        String deductSql = "UPDATE holdings SET current_value = current_value - ? WHERE investor_id = ? AND asset_class = ?";
        String addSql = "UPDATE holdings SET current_value = current_value + ? WHERE investor_id = ? AND asset_class = ?";

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setLong(1, investorId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    while (rs.next()) {
                        String assetClass = rs.getString("asset_class");
                        double totalValue = rs.getDouble("total_value");
                        System.out.println(assetClass + ": " + totalValue);
                    }
                }
            }

            double shiftAmount = 5000.0;

            try (PreparedStatement deductStmt = conn.prepareStatement(deductSql)) {
                deductStmt.setDouble(1, shiftAmount);
                deductStmt.setLong(2, investorId);
                deductStmt.setString(3, "Debt");
                deductStmt.executeUpdate();
            }

            try (PreparedStatement addStmt = conn.prepareStatement(addSql)) {
                addStmt.setDouble(1, shiftAmount);
                addStmt.setLong(2, investorId);
                addStmt.setString(3, "Equity");
                addStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}