package com.oceanview.eresort.controller;

import com.oceanview.eresort.dto.ReservationRequest;
import com.oceanview.eresort.dto.ReservationResponse;
import com.oceanview.eresort.model.Reservation;
import com.oceanview.eresort.model.RoomType;
import com.oceanview.eresort.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for reservation management.
 * Provides CRUD operations and search functionality.
 */
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Get all reservations.
     * GET /api/reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations(
            @RequestParam(required = false) String guestName,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {
        
        List<Reservation> reservations;

        // Handle search parameters
        if (guestName != null && !guestName.isEmpty()) {
            reservations = reservationService.searchByGuestName(guestName);
        } else if (roomType != null && !roomType.isEmpty()) {
            reservations = reservationService.searchByRoomType(RoomType.fromString(roomType));
        } else if (fromDate != null && toDate != null) {
            reservations = reservationService.searchByDateRange(
                    LocalDate.parse(fromDate),
                    LocalDate.parse(toDate)
            );
        } else {
            reservations = reservationService.getAllReservations();
        }

        List<ReservationResponse> response = reservations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Get reservation by ID.
     * GET /api/reservations/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable String id) {
        return reservationService.findReservation(id)
                .map(reservation -> ResponseEntity.ok(toResponse(reservation)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create new reservation.
     * POST /api/reservations
     */
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request) {
        
        try {
            Reservation reservation = reservationService.createReservation(
                    request.getGuestName(),
                    request.getAddress(),
                    request.getContactNumber(),
                    RoomType.fromString(request.getRoomType()),
                    LocalDate.parse(request.getCheckInDate()),
                    LocalDate.parse(request.getCheckOutDate())
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(reservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update existing reservation.
     * PUT /api/reservations/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable String id,
            @Valid @RequestBody ReservationRequest request) {
        
        try {
            Reservation updatedReservation = new Reservation();
            updatedReservation.setGuestName(request.getGuestName());
            updatedReservation.setAddress(request.getAddress());
            updatedReservation.setContactNumber(request.getContactNumber());
            updatedReservation.setRoomType(RoomType.fromString(request.getRoomType()));
            updatedReservation.setCheckInDate(LocalDate.parse(request.getCheckInDate()));
            updatedReservation.setCheckOutDate(LocalDate.parse(request.getCheckOutDate()));

            Reservation reservation = reservationService.updateReservation(id, updatedReservation);
            return ResponseEntity.ok(toResponse(reservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete reservation.
     * DELETE /api/reservations/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        if (reservationService.deleteReservation(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Convert Reservation entity to ReservationResponse DTO.
     */
    private ReservationResponse toResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setReservationNumber(reservation.getReservationNumber());
        response.setGuestName(reservation.getGuestName());
        response.setAddress(reservation.getAddress());
        response.setContactNumber(reservation.getContactNumber());
        response.setRoomType(reservation.getRoomType().name());
        response.setCheckInDate(reservation.getCheckInDate().toString());
        response.setCheckOutDate(reservation.getCheckOutDate().toString());
        response.setTotalAmount(reservation.getTotalAmount());
        return response;
    }
}
