package com.oceanview.eresort.service;

import com.oceanview.eresort.model.Reservation;
import com.oceanview.eresort.model.RoomType;
import com.oceanview.eresort.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("Create reservation with Suite for 3 nights should calculate LKR 60,000")
    void createReservation_suite3Nights_calculatesCorrectAmount() {
        // Arrange
        when(reservationRepository.countReservationsForDate(any())).thenReturn(0L);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Reservation reservation = reservationService.createReservation(
                "John Silva",
                "Galle",
                "0771234567",
                RoomType.SUITE,
                LocalDate.of(2026, 3, 6),
                LocalDate.of(2026, 3, 9)
        );

        // Assert
        assertEquals(60000, reservation.getTotalAmount());
        assertEquals(3, reservation.getNumberOfNights());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    @DisplayName("Create reservation with invalid dates should throw exception")
    void createReservation_invalidDates_throwsException() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2026, 3, 10);
        LocalDate checkOut = LocalDate.of(2026, 3, 8);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.createReservation(
                    "Test Guest",
                    "Test Address",
                    "0771234567",
                    RoomType.SINGLE,
                    checkIn,
                    checkOut
            );
        });
    }

    @Test
    @DisplayName("Create reservation with empty guest name should throw exception")
    void createReservation_emptyGuestName_throwsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.createReservation(
                    "",
                    "Test Address",
                    "0771234567",
                    RoomType.SINGLE,
                    LocalDate.of(2026, 3, 10),
                    LocalDate.of(2026, 3, 12)
            );
        });
    }
}
