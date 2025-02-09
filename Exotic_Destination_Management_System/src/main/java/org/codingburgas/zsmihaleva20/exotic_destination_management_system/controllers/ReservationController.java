package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final DestinationRepository destinationRepository;

    public ReservationController(ReservationRepository reservationRepository, DestinationRepository destinationRepository) {
        this.reservationRepository = reservationRepository;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/reservation/{id}")
    public String showReservationForm(@PathVariable Long id, Model model) {
        Destination destination = destinationRepository.findById(id).orElseThrow();
        model.addAttribute("destination", destination);
        return "reservation";
    }

    @PostMapping("/destinations")
    public String submitReservation(@RequestParam Long destinationId,
                                    @RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam int numberOfPeople,
                                    @RequestParam String cardName,
                                    @RequestParam String cardNumber,
                                    @RequestParam String expiryDate,
                                    @RequestParam String cvc)  {

        Destination destination = destinationRepository.findById(destinationId).orElseThrow();

        if (destination.getRemainingPeople() < numberOfPeople) {
            return "redirect:/reservation?error=LimitExceeded";
        }

        Reservation reservation = new Reservation();
        reservation.setDestination(destination);
        reservation.setName(name);
        reservation.setEmail(email);
        reservation.setNumberOfPeople(numberOfPeople);
        /*reservation.setCardName(cardName);
        reservation.setCardNumber(cardNumber);
        reservation.setExpiryDate(expiryDate);
        reservation.setCvc(cvc);*/
        reservation.setStatus("BOOKED");

        reservationRepository.save(reservation);
        double totalPrice = destination.getPrice() * numberOfPeople;
        reservation.setTotalPrice(totalPrice);
        destination.incrementPopularity(numberOfPeople);
        destination.setRemainingPeople(destination.getRemainingPeople() - numberOfPeople);
        destinationRepository.save(destination);

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

        return "redirect:/myReservations";
    }

    @GetMapping("/myReservations")
    public String viewUserReservations(Model model) {
        List<Reservation> reservations = reservationRepository.findAll();
        model.addAttribute("reservations", reservations);
        return "myReservations";
    }

}
