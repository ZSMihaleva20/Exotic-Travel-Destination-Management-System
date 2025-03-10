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

    public ReservationNotificationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getUrgentReservations(User user) {
        LocalDate today = LocalDate.now();
        LocalDate urgentThreshold = today.plusDays(3);

        List<Reservation> allReservations = reservationRepository.findByUser(user);

        return allReservations.stream()
                .filter(reservation -> "BOOKED".equals(reservation.getStatus()) &&
                        !reservation.getDestination().getDateOfDeparture().isBefore(today) &&
                        !reservation.getDestination().getDateOfDeparture().isAfter(urgentThreshold))
                .collect(Collectors.toList());
    }
}
