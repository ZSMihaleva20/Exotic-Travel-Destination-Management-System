package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Rating;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class RatingController {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/rateDestination")
    public String showRatingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Get the logged-in user

        List<Reservation> reservations = reservationRepository.findByUser(user);
        model.addAttribute("reservations", reservations);

        return "rateDestination";
    }

    @PostMapping("/rateDestination")
    public String rateDestination(@RequestParam Long destinationId, @RequestParam int stars) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Destination destination = destinationRepository.findById(destinationId).orElse(null);

        if (destination != null) {
            List<Reservation> reservations = reservationRepository.findByUser(user);
            for (Reservation reservation : reservations) {
                if (reservation.getDestination().equals(destination) && !reservation.isDestinationRated()) {
                    reservation.setDestinationRated(true);
                    reservation.setDestinationRating(stars);
                    reservationRepository.save(reservation);

                    destination.addRating(stars);
                    destinationRepository.save(destination);
                    break;
                }
            }
        }

        return "redirect:/rateDestination";
    }
}
