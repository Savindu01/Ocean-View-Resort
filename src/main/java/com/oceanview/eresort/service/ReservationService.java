package com.oceanview.eresort.service;

import com.oceanview.eresort.model.Reservation;
import com.oceanview.eresort.model.RoomType;
import com.oceanview.eresort.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for reservation management.
 * Contains business logic for reservation operations.
 */
@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Create a new reservation with auto-generated reservation number.
     */
    public Reservation createReservation(String guestName, String address, String contactNumber,
                                        RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        // Validate dates
        validateDates(checkInDate, checkOutDate);

        // Validate inputs
        validateReservationInputs(guestName, address, contactNumber);

        // Generate reservation number
        String reservationNumber = generateReservationNumber();

        // Create reservation
        Reservation reservation = new Reservation(
                reservationNumber, guestName, address, contactNumber,
                roomType, checkInDate, checkOutDate
        );

        return reservationRepository.save(reservation);
    }

    /**
     * Find reservation by reservation number.
     */
    public Optional<Reservation> findReservation(String reservationNumber) {
        return reservationRepository.findById(reservationNumber);
    }

    /**
     * Get all reservations.
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAllByOrderByCheckInDateDesc();
    }

    /**
     * Update existing reservation.
     */
    public Reservation updateReservation(String reservationNumber, Reservation updatedReservation) {
        return reservationRepository.findById(reservationNumber)
                .map(existing -> {
                    existing.setGuestName(updatedReservation.getGuestName());
                    existing.setAddress(updatedReservation.getAddress());
                    existing.setContactNumber(updatedReservation.getContactNumber());
                    existing.setRoomType(updatedReservation.getRoomType());
                    existing.setCheckInDate(updatedReservation.getCheckInDate());
                    existing.setCheckOutDate(updatedReservation.getCheckOutDate());
                    
                    // Validate dates
                    validateDates(existing.getCheckInDate(), existing.getCheckOutDate());
                    
                    return reservationRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationNumber));
    }

    /**
     * Delete reservation.
     */
    public boolean deleteReservation(String reservationNumber) {
        if (reservationRepository.existsById(reservationNumber)) {
            reservationRepository.deleteById(reservationNumber);
            return true;
        }
        return false;
    }

    /**
     * Search reservations by guest name.
     */
    public List<Reservation> searchByGuestName(String guestName) {
        return reservationRepository.findByGuestNameContainingIgnoreCase(guestName);
    }

    /**
     * Search reservations by room type.
     */
    public List<Reservation> searchByRoomType(RoomType roomType) {
        return reservationRepository.findByRoomType(roomType);
    }

    /**
     * Search reservations by date range.
     */
    public List<Reservation> searchByDateRange(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findOverlappingReservations(startDate, endDate);
    }

    /**
     * Generate unique reservation number in format: RES-YYYYMMDD-XXX
     */
    private String generateReservationNumber() {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.BASIC_ISO_DATE);
        
        long count = reservationRepository.countReservationsForDate(today);
        String sequence = String.format("%03d", count + 1);
        
        return "RES-" + dateStr + "-" + sequence;
    }

    /**
     * Validate check-in and check-out dates.
     */
    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required");
        }
        
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }

    /**
     * Validate reservation inputs.
     */
    private void validateReservationInputs(String guestName, String address, String contactNumber) {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name is required");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required");
        }
    }
}
