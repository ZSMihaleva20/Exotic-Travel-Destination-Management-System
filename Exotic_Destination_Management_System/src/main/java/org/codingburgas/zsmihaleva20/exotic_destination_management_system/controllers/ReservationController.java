package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import jakarta.mail.MessagingException;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.MailAndPdfService;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.ReservationNotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final DestinationRepository destinationRepository;
    private final MailAndPdfService mailAndPdfService;
    private final ReservationNotificationService reservationNotificationService;

    public ReservationController(ReservationRepository reservationRepository, DestinationRepository destinationRepository, MailAndPdfService mailAndPdfService, ReservationNotificationService reservationNotificationService) {
        this.reservationRepository = reservationRepository;
        this.destinationRepository = destinationRepository;
        this.mailAndPdfService = mailAndPdfService;
        this.reservationNotificationService = reservationNotificationService;
    }

    @GetMapping("/reservation/{id}")
    public String showReservationForm(@PathVariable Long id, Model model) {
        Destination destination = destinationRepository.findById(id).orElseThrow();
        model.addAttribute("destination", destination);
        return "reservation";
    }

    @PostMapping("/destinations")
    public String submitReservation(@RequestParam Long destinationId,
                                    @RequestParam int numberOfPeople) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Get the logged-in user

        Destination destination = destinationRepository.findById(destinationId).orElseThrow();

        if (destination.getRemainingPeople() < numberOfPeople) {
            return "redirect:/reservation?error=LimitExceeded";
        }

        Reservation reservation = new Reservation();
        reservation.setDestination(destination);
        reservation.setUser(user);  // Associate reservation with logged-in user
        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setTotalPrice(destination.getPrice() * numberOfPeople);
        reservation.setStatus("BOOKED");

        reservationRepository.save(reservation);

        destination.incrementPopularity(numberOfPeople);
        destination.setRemainingPeople(destination.getRemainingPeople() - numberOfPeople);
        destinationRepository.save(destination);

        try {
            mailAndPdfService.sendConfirmationMail(reservation, user);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/destinations";
    }

    @PostMapping("/cancel-reservation/{id}")
    public String cancelReservation(@PathVariable Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        reservation.setStatus("CANCELED");
        Destination destination = reservation.getDestination();
        destination.decrementPopularity(reservation.getNumberOfPeople());
        destination.setRemainingPeople(destination.getRemainingPeople() + reservation.getNumberOfPeople());
        destinationRepository.save(destination);

        try {
            mailAndPdfService.sendCancelationMail(reservation, reservation.getUser());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/myReservations";
    }

    @GetMapping("/myReservations")
    public String viewUserReservations(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Get current user

        List<Reservation> allReservations = reservationRepository.findByUser(user);
        LocalDate now = LocalDate.now();

        // Active Reservations: Departure AND Return Date are BOTH after today
        List<Reservation> activeReservations = allReservations.stream()
                .filter(res -> "BOOKED".equals(res.getStatus()) &&
                        res.getDestination().getDateOfDeparture().isAfter(now) &&
                        res.getDestination().getDateOfReturn().isAfter(now))
                .toList();

        // Canceled Reservations
        List<Reservation> canceledReservations = allReservations.stream()
                .filter(res -> "CANCELED".equals(res.getStatus()))
                .toList();

        // Past Reservations: Departure and Return Date are BOTH before today OR ONLY Departure is before today
        List<Reservation> pastReservations = allReservations.stream()
                .filter(res -> "BOOKED".equals(res.getStatus()) &&
                        (res.getDestination().getDateOfDeparture().isBefore(now) ||
                                res.getDestination().getDateOfDeparture().isEqual(now) ||
                                        res.getDestination().getDateOfReturn().isBefore(now)))
                .toList();

        // 🔔 **Urgent Reservations (Departing in 3 days or less)**
        List<Reservation> urgentReservations = reservationNotificationService.getUrgentReservations(user);

        model.addAttribute("activeReservations", activeReservations);
        model.addAttribute("canceledReservations", canceledReservations);
        model.addAttribute("pastReservations", pastReservations);
        model.addAttribute("urgentReservations", urgentReservations); // Add this for notifications

        return "myReservations";
    }



}
