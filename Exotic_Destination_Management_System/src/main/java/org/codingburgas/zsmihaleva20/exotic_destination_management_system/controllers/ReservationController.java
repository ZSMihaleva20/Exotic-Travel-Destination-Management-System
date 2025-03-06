package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import jakarta.mail.MessagingException;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.MailService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final DestinationRepository destinationRepository;
    private final MailService mailService;

    public ReservationController(ReservationRepository reservationRepository, DestinationRepository destinationRepository, MailService mailService) {
        this.reservationRepository = reservationRepository;
        this.destinationRepository = destinationRepository;
        this.mailService = mailService;
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
            mailService.sendConfirmationMail(reservation, user);
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
            mailService.sendCancelationMail(reservation, reservation.getUser());
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
                                (res.getDestination().getDateOfDeparture().isEqual(now) &&
                                        res.getDestination().getDateOfReturn().isBefore(now))))
                .toList();

        model.addAttribute("activeReservations", activeReservations);
        model.addAttribute("canceledReservations", canceledReservations);
        model.addAttribute("pastReservations", pastReservations);

        return "myReservations";
    }

    @GetMapping("/download-reservation/{id}")
    public ResponseEntity<Resource> downloadReservationPdf(@PathVariable Long id) throws IOException, MessagingException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        User user = reservation.getUser();

        // Generate the PDF as a byte array (not saved to file)
        byte[] pdfBytes = mailService.generateReservationPdf(reservation, user);

        // Create a resource from the byte array
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfBytes));

        // Return the PDF as a downloadable file
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reservation_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }


}
