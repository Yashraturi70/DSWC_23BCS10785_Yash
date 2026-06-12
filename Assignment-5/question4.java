// SELECT r.rider_name, r.bike_model, ranked_pings.latitude, ranked_pings.longitude, ranked_pings.recorded_at
// FROM riders r
// INNER JOIN (
//     SELECT rider_id, latitude, longitude, recorded_at,
//            ROW_NUMBER() OVER (PARTITION BY rider_id ORDER BY recorded_at DESC) as rn
//     FROM gps_pings
// ) ranked_pings ON r.rider_id = ranked_pings.rider_id
// WHERE ranked_pings.rn = 1;

// CREATE INDEX idx_gps_pings_rider_recorded ON gps_pings (rider_id, recorded_at DESC);


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

interface TelemetryService {
    void printLatestLocations();
}

abstract class FleetDatabaseConnection {
    private final String url = "jdbc:postgresql://localhost:3000/fleet_db";
    private final String user = "postgres";
    private final String password = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

class TelemetryServiceImpl extends FleetDatabaseConnection implements TelemetryService {
    @Override
    public void printLatestLocations() {
        String sql = "SELECT r.rider_name, r.bike_model, ranked_pings.latitude, ranked_pings.longitude, ranked_pings.recorded_at " +
                     "FROM riders r " +
                     "INNER JOIN (" +
                     "  SELECT rider_id, latitude, longitude, recorded_at, " +
                     "         ROW_NUMBER() OVER (PARTITION BY rider_id ORDER BY recorded_at DESC) as rn " +
                     "  FROM gps_pings" +
                     ") ranked_pings ON r.rider_id = ranked_pings.rider_id " +
                     "WHERE ranked_pings.rn = 1";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String riderName = rs.getString("rider_name");
                String bikeModel = rs.getString("bike_model");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                LocalDateTime recordedAt = rs.getObject("recorded_at", LocalDateTime.class);

                System.out.println(riderName + " (" + bikeModel + ") -> Lat: " + latitude + ", Lon: " + longitude + " @ " + recordedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}