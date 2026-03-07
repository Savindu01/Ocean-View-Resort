package com.oceanview.eresort.repository;

import com.oceanview.eresort.model.Reservation;
import com.oceanview.eresort.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for Reservation entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    /**
     * Find reservations by guest name (case-insensitive, partial match).
     * Spring Data JPA generates: SELECT * FROM reservations WHERE LOWER(guest_name) LIKE LOWER(?)
     */
    List<Reservation> findByGuestNameContainingIgnoreCase(String guestName);

    /**
     * Find reservations by room type.
     */
    List<Reservation> findByRoomType(RoomType roomType);

    /**
     * Find reservations by check-in date range.
     */
    List<Reservation> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find reservations by check-out date range.
     */
    List<Reservation> findByCheckOutDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find reservations that overlap with a given date range.
     * Custom JPQL query for complex date range logic.
     */
    @Query("SELECT r FROM Reservation r WHERE " +
           "(r.checkInDate <= :endDate AND r.checkOutDate >= :startDate)")
    List<Reservation> findOverlappingReservations(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Count reservations for a specific date (for reservation number generation).
     */
    @Query("SELECT COUNT(r) FROM Reservation r WHERE " +
           "FUNCTION('DATE', r.createdAt) = :date")
    long countReservationsForDate(@Param("date") LocalDate date);

    /**
     * Find all reservations ordered by check-in date descending.
     */
    List<Reservation> findAllByOrderByCheckInDateDesc();

    /**
     * Find reservations by contact number.
     */
    List<Reservation> findByContactNumber(String contactNumber);
}
