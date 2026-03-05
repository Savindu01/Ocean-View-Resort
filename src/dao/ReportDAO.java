package dao;

import model.RoomType;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Data Access Object for generating hotel reports.
 */
public class ReportDAO {
    private final Connection connection;

    public ReportDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Get occupancy report for a date range.
     * Returns occupancy percentage for each room type.
     */
    public Map<RoomType, Map<String, Object>> getOccupancyReport(LocalDate fromDate, LocalDate toDate) {
        Map<RoomType, Map<String, Object>> report = new HashMap<>();

        // Total days in range
        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(fromDate, toDate);
        if (totalDays <= 0) totalDays = 1;

        for (RoomType roomType : RoomType.values()) {
            String sql = "SELECT COUNT(*) as room_count FROM " +
                        "(SELECT DISTINCT DATE_ADD(check_in_date, INTERVAL seq-1 DAY) as day FROM reservations " +
                        "CROSS JOIN (SELECT @seq:=0) init WHERE room_type = ? " +
                        "AND check_in_date <= ? AND check_out_date > ?) as occupied_days";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, roomType.name());
                stmt.setDate(2, java.sql.Date.valueOf(toDate));
                stmt.setDate(3, java.sql.Date.valueOf(fromDate));

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    long occupiedDays = rs.getLong("room_count");
                    double occupancyPercent = (occupiedDays * 100.0) / totalDays;

                    Map<String, Object> roomData = new HashMap<>();
                    roomData.put("roomType", roomType.getDisplayName());
                    roomData.put("occupiedDays", occupiedDays);
                    roomData.put("totalDays", totalDays);
                    roomData.put("occupancyPercent", Math.round(occupancyPercent * 100.0) / 100.0);

                    report.put(roomType, roomData);
                }
            } catch (SQLException e) {
                System.err.println("Error generating occupancy report: " + e.getMessage());
            }
        }

        return report;
    }

    /**
     * Get revenue report for a date range.
     */
    public Map<String, Object> getRevenueReport(LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> report = new HashMap<>();
        report.put("fromDate", fromDate);
        report.put("toDate", toDate);
        report.put("roomTypeBreakdown", new HashMap<String, Object>());

        long totalRevenue = 0;
        String sql = "SELECT room_type, SUM((DATEDIFF(check_out_date, check_in_date) * rate)) as revenue, " +
                     "COUNT(*) as reservations FROM " +
                     "(SELECT r.*, rt.price_per_night as rate FROM reservations r " +
                     "LEFT JOIN room_rates rt ON r.room_type = rt.room_type) as data " +
                     "WHERE check_in_date >= ? AND check_out_date <= ? GROUP BY room_type";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(fromDate));
            stmt.setDate(2, java.sql.Date.valueOf(toDate));

            ResultSet rs = stmt.executeQuery();
            Map<String, Object> breakdown = (Map<String, Object>) report.get("roomTypeBreakdown");

            while (rs.next()) {
                String roomType = rs.getString("room_type");
                long revenue = rs.getLong("revenue");
                int reservations = rs.getInt("reservations");

                Map<String, Object> roomData = new HashMap<>();
                roomData.put("revenue", revenue);
                roomData.put("reservations", reservations);

                breakdown.put(roomType, roomData);
                totalRevenue += revenue;
            }
        } catch (SQLException e) {
            System.err.println("Error generating revenue report: " + e.getMessage());
        }

        report.put("totalRevenue", totalRevenue);
        return report;
    }

    /**
     * Get check-in/check-out summary for a specific date.
     */
    public Map<String, Object> getDailyCheckInCheckOut(LocalDate date) {
        Map<String, Object> summary = new HashMap<>();
        summary.put("date", date);
        summary.put("checkIns", new ArrayList<>());
        summary.put("checkOuts", new ArrayList<>());

        // Check-ins
        String checkInSql = "SELECT * FROM reservations WHERE check_in_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(checkInSql)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            List<String> checkIns = (List<String>) summary.get("checkIns");
            while (rs.next()) {
                checkIns.add(rs.getString("guest_name") + " (" + 
                           rs.getString("room_type") + ") - Res: " + 
                           rs.getString("reservation_number"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching check-ins: " + e.getMessage());
        }

        // Check-outs
        String checkOutSql = "SELECT * FROM reservations WHERE check_out_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(checkOutSql)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            List<String> checkOuts = (List<String>) summary.get("checkOuts");
            while (rs.next()) {
                checkOuts.add(rs.getString("guest_name") + " (" + 
                            rs.getString("room_type") + ") - Res: " + 
                            rs.getString("reservation_number"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching check-outs: " + e.getMessage());
        }

        return summary;
    }
}
