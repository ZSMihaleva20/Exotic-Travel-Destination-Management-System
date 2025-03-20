package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationNotificationService {

    private final ReservationRepository reservationRepository;

    // Constructor to inject the reservation repository
    public ReservationNotificationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Retrieves a list of urgent reservations for the given user
    // A reservation is considered urgent if the departure date is within the next 3 days and the reservation status is "BOOKED"
    public List<Reservation> getUrgentReservations(User user) {
        LocalDate today = LocalDate.now();
        LocalDate urgentThreshold = today.plusDays(3);

        List<Reservation> allReservations = reservationRepository.findByUser(user);

        // Filter for reservations that are booked and whose departure date is within the next 3 days
        return allReservations.stream()
                .filter(reservation -> "BOOKED".equals(reservation.getStatus()) &&
                        !reservation.getDestination().getDateOfDeparture().isBefore(today) && // Departure is after today
                        !reservation.getDestination().getDateOfDeparture().isEqual(today) &&  // Departure is not today
                        !reservation.getDestination().getDateOfDeparture().isAfter(urgentThreshold)) // Departure is before or equal to the urgent threshold
                .collect(Collectors.toList());
    }
}
