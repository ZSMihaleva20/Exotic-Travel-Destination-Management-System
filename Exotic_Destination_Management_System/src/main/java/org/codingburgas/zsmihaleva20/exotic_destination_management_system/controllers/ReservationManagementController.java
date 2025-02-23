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
        List<Reservation> bookedReservations = reservationRepository.findByStatus("BOOKED");
        List<Reservation> canceledReservations = reservationRepository.findByStatus("CANCELED");
        List<Destination> allDestinations = destinationRepository.findAll(); // Fetch all destinations

        // Group reservations by destination
        Map<Destination, List<Reservation>> reservationsByDestination = bookedReservations.stream()
                .collect(Collectors.groupingBy(Reservation::getDestination));

        model.addAttribute("reservationsByDestination", reservationsByDestination);
        model.addAttribute("canceledReservations", canceledReservations);
        model.addAttribute("allDestinations", allDestinations); // Add all destinations

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
