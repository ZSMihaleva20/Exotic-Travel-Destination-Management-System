package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import jakarta.mail.MessagingException;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ReservationManagementController {
    private final ReservationRepository reservationRepository;
    private final MailService mailService;
    private final DestinationRepository destinationRepository;

    public ReservationManagementController(ReservationRepository reservationRepository, MailService mailService, DestinationRepository destinationRepository) {
        this.reservationRepository = reservationRepository;
        this.mailService = mailService;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/reservationManagement")
    public String viewAllReservations(Model model) {
        List<Reservation> allReservations = reservationRepository.findAll();
        LocalDate now = LocalDate.now();

        // Active Reservations: Both Departure AND Return Date are in the future
        List<Reservation> activeReservations = allReservations.stream()
                .filter(res -> "BOOKED".equals(res.getStatus()) &&
                        res.getDestination().getDateOfDeparture().isAfter(now) &&
                        res.getDestination().getDateOfReturn().isAfter(now))
                .toList();

        // Canceled Reservations
        List<Reservation> canceledReservations = allReservations.stream()
                .filter(res -> "CANCELED".equals(res.getStatus()))
                .toList();

        // Past Reservations:
        // - Both departure and return dates are before OR on today
        // - OR only the departure date is before or on today
        List<Reservation> pastReservations = allReservations.stream()
                .filter(res -> "BOOKED".equals(res.getStatus()) &&
                        (res.getDestination().getDateOfDeparture().isBefore(now) ||
                                (res.getDestination().getDateOfDeparture().isEqual(now) &&
                                        res.getDestination().getDateOfReturn().isBefore(now))))
                .toList();

        model.addAttribute("activeReservations", activeReservations);
        model.addAttribute("canceledReservations", canceledReservations);
        model.addAttribute("pastReservations", pastReservations);

        return "reservationManagement";
    }



    @PostMapping("/cancelReservation/{id}")
    public String cancelReservationManagement(@PathVariable Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        reservation.setStatus("CANCELED");
        Destination destination = reservation.getDestination();
        destination.decrementPopularity(reservation.getNumberOfPeople());
        destination.setRemainingPeople(destination.getRemainingPeople() + reservation.getNumberOfPeople());
        destinationRepository.save(destination);

        try {
            mailService.sendCancelationMail(reservation, reservation.getUser());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/reservationManagement";
    }
}
