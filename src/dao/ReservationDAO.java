package dao;

import model.Reservation;
import model.RoomType;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Reservation CRUD operations.
 */
public class ReservationDAO {
    private final Connection connection;

    public ReservationDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Generate a unique reservation number.
     * Format: RES-YYYYMMDD-XXX
     */
    public String generateReservationNumber() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "RES-" + dateStr + "-";

        String sql = "SELECT reservation_number FROM reservations WHERE reservation_number LIKE ? " +
                     "ORDER BY reservation_number DESC LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String lastNumber = rs.getString("reservation_number");
                int sequence = Integer.parseInt(lastNumber.substring(lastNumber.lastIndexOf("-") + 1));
                return prefix + String.format("%03d", sequence + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error generating reservation number: " + e.getMessage());
        }

        return prefix + "001";
    }

    /**
     * Create a new reservation.
     */
    public boolean create(Reservation reservation) {
        String sql = "INSERT INTO reservations (reservation_number, guest_name, address, " +
                     "contact_number, room_type, check_in_date, check_out_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, reservation.getReservationNumber());
            stmt.setString(2, reservation.getGuestName());
            stmt.setString(3, reservation.getAddress());
            stmt.setString(4, reservation.getContactNumber());
            stmt.setString(5, reservation.getRoomType().name());
            stmt.setDate(6, Date.valueOf(reservation.getCheckInDate()));
            stmt.setDate(7, Date.valueOf(reservation.getCheckOutDate()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating reservation: " + e.getMessage());
        }
        return false;
    }

    /**
     * Find a reservation by reservation number.
     */
    public Reservation findByReservationNumber(String reservationNumber) {
        String sql = "SELECT * FROM reservations WHERE reservation_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, reservationNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding reservation: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all reservations.
     */
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations ORDER BY created_at DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving reservations: " + e.getMessage());
        }
        return reservations;
    }

    /**
     * Update an existing reservation.
     */
    public boolean update(Reservation reservation) {
        String sql = "UPDATE reservations SET guest_name = ?, address = ?, contact_number = ?, " +
                     "room_type = ?, check_in_date = ?, check_out_date = ? " +
                     "WHERE reservation_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, reservation.getGuestName());
            stmt.setString(2, reservation.getAddress());
            stmt.setString(3, reservation.getContactNumber());
            stmt.setString(4, reservation.getRoomType().name());
            stmt.setDate(5, Date.valueOf(reservation.getCheckInDate()));
            stmt.setDate(6, Date.valueOf(reservation.getCheckOutDate()));
            stmt.setString(7, reservation.getReservationNumber());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating reservation: " + e.getMessage());
        }
        return false;
    }

    /**
     * Delete a reservation.
     */
    public boolean delete(String reservationNumber) {
        String sql = "DELETE FROM reservations WHERE reservation_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, reservationNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting reservation: " + e.getMessage());
        }
        return false;
    }

    /**
     * Search reservations by guest name.
     */
    public List<Reservation> searchByGuestName(String guestName) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE guest_name LIKE ? ORDER BY created_at DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + guestName + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching reservations: " + e.getMessage());
        }
        return reservations;
    }

    /**
     * Search reservations by date range.
     */
    public List<Reservation> searchByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE check_in_date >= ? AND check_out_date <= ? ORDER BY check_in_date";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fromDate));
            stmt.setDate(2, Date.valueOf(toDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching reservations by date: " + e.getMessage());
        }
        return reservations;
    }

    /**
     * Search reservations by room type.
     */
    public List<Reservation> searchByRoomType(RoomType roomType) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE room_type = ? ORDER BY created_at DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, roomType.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching by room type: " + e.getMessage());
        }
        return reservations;
    }

    /**
     * Check if a room is available for the given dates.
     * @param roomType the room type to check
     * @param checkInDate check-in date
     * @param checkOutDate check-out date
     * @param excludeReservation reservation number to exclude (for updates), or null
     * @return true if room is available, false otherwise
     */
    public boolean isRoomAvailable(RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate, String excludeReservation) {
        String sql = "SELECT COUNT(*) as count FROM reservations WHERE room_type = ? " +
                     "AND check_in_date < ? AND check_out_date > ?";
        
        if (excludeReservation != null) {
            sql += " AND reservation_number != ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, roomType.name());
            stmt.setDate(2, Date.valueOf(checkOutDate));
            stmt.setDate(3, Date.valueOf(checkInDate));
            
            if (excludeReservation != null) {
                stmt.setString(4, excludeReservation);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") == 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking room availability: " + e.getMessage());
        }
        return false;
    }

    /**
     * Map ResultSet to Reservation object.
     */
    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationNumber(rs.getString("reservation_number"));
        reservation.setGuestName(rs.getString("guest_name"));
        reservation.setAddress(rs.getString("address"));
        reservation.setContactNumber(rs.getString("contact_number"));
        reservation.setRoomType(RoomType.valueOf(rs.getString("room_type")));
        reservation.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        reservation.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        reservation.setCreatedAt(rs.getTimestamp("created_at"));
        return reservation;
    }
}
