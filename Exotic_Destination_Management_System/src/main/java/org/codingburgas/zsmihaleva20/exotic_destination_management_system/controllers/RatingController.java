package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Rating;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.RatingRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;


@Controller
public class RatingController {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping("/rateDestination")
    public String showRatingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Get the logged-in user

        List<Reservation> reservations = reservationRepository.findByUser(user);
        model.addAttribute("reservations", reservations);

        return "rateDestination";
    }

    @PostMapping("/rateDestination")
    public String rateDestination(@RequestParam Long destinationId, @RequestParam int stars, @RequestParam String comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Destination destination = destinationRepository.findById(destinationId).orElse(null);

        if (destination != null) {
            List<Reservation> reservations = reservationRepository.findByUser(user);
            for (Reservation reservation : reservations) {
                if (reservation.getDestination().equals(destination) && !reservation.isDestinationRated()) {
                    reservation.setDestinationRated(true);
                    reservation.setDestinationRating(stars);
                    reservation.setComment(comment); // Save the comment in reservation
                    reservationRepository.save(reservation);

                    Rating rating = new Rating(destination, user, stars, comment);
                    rating.setTimestamp(LocalDateTime.now());
                    ratingRepository.save(rating);

                    destination.addRating(stars);
                    destinationRepository.save(destination);
                    break;
                }
            }
        }

        return "redirect:/rateDestination";
    }

    @GetMapping("/ratingManagement")
    public String showRatingManagementPage(Model model) {
        List<Destination> allDestinations = destinationRepository.findAll();
        List<Rating> allRatings = ratingRepository.findAll();

        // Map destinations to their ratings
        Map<Destination, List<Rating>> ratingsByDestination = new HashMap<>();
        for (Rating rating : allRatings) {
            ratingsByDestination
                    .computeIfAbsent(rating.getDestination(), k -> new ArrayList<>())
                    .add(rating);
        }

        model.addAttribute("allDestinations", allDestinations);
        model.addAttribute("ratingsByDestination", ratingsByDestination);

        return "ratingManagement";
    }

    @PostMapping("/deleteRating/{ratingId}")
    public String deleteRating(@PathVariable Long ratingId) {
        Optional<Rating> ratingOpt = ratingRepository.findById(ratingId);

        if (ratingOpt.isPresent()) {
            Rating rating = ratingOpt.get();
            Destination destination = rating.getDestination();
            User user = rating.getUser();

            // Remove rating from the destination
            destination.setRatingSum(destination.getRatingSum() - rating.getStars());
            destination.setRatingCount(destination.getRatingCount() - 1);

            // Ensure rating count doesn't go negative
            if (destination.getRatingCount() < 0) {
                destination.setRatingCount(0);
            }

            // Recalculate the average rating
            destination.getAverageRating();

            // Save the updated destination
            destinationRepository.save(destination);

            // Find and update the reservation related to this rating
            List<Reservation> reservations = reservationRepository.findByUser(user);
            for (Reservation reservation : reservations) {
                if (reservation.getDestination().equals(destination) && reservation.isDestinationRated()) {
                    reservation.setDestinationRated(false);
                    reservation.setDestinationRating(0);
                    reservationRepository.save(reservation);
                    break;
                }
            }

            // Delete the rating from the database
            ratingRepository.delete(rating);
        }

        return "redirect:/ratingManagement";
    }
}
