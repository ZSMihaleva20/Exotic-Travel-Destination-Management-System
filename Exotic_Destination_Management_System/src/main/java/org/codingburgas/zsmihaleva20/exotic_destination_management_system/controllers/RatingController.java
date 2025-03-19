package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Rating;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.RatingRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Controller
public class RatingController {

    // Repositories to interact with the database for destinations, reservations, and ratings
    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    // Displays the page for users to rate destinations they've visited
    @GetMapping("/rateDestination")
    public String showRatingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Get the logged-in user
        LocalDate now = LocalDate.now();

        // Get past reservations for the user
        List<Reservation> pastReservations = reservationRepository.findByUser(user).stream()
                .filter(reservation -> reservation.getDestination().getDateOfReturn().isBefore(now) ||
                        reservation.getDestination().getDateOfReturn().isEqual(now))
                .toList();

        model.addAttribute("reservations", pastReservations); // Add the list of past reservations to the model
        return "rateDestination";
    }

    // Handles submitting a rating for a destination
    @PostMapping("/rateDestination")
    public String rateDestination(@RequestParam Long destinationId, @RequestParam int stars, @RequestParam String comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // Find the destination to be rated
        Destination destination = destinationRepository.findById(destinationId).orElse(null);

        if (destination != null) {
            // Check if the user has a reservation for this destination and has not rated it
            List<Reservation> reservations = reservationRepository.findByUser(user);
            for (Reservation reservation : reservations) {
                if (reservation.getDestination().equals(destination) && !reservation.isDestinationRated()) {
                    reservation.setDestinationRated(true); // Mark the destination as rated
                    reservation.setDestinationRating(stars); // Set the rating on the reservation
                    reservation.setComment(comment); // Save the comment in reservation
                    reservationRepository.save(reservation);

                    // Create and save a new Rating object
                    Rating rating = new Rating(destination, user, stars, comment);
                    rating.setTimestamp(LocalDateTime.now());
                    ratingService.saveRating(rating);

                    // Update the destination's rating
                    destinationRepository.save(destination);
                    break; // Break after processing the first matching reservation
                }
            }
        }

        return "redirect:/rateDestination"; // Redirect back to the rateDestination page
    }

    // Displays the page for managing all ratings
    @GetMapping("/ratingManagement")
    public String showRatingManagementPage(Model model,  @AuthenticationPrincipal User user) {
        // Get all destinations and ratings from the repository
        List<Destination> allDestinations = destinationRepository.findAll();
        List<Rating> allRatings = ratingRepository.findAll();

        // Map destinations to their corresponding ratings
        Map<Destination, List<Rating>> ratingsByDestination = new HashMap<>();
        for (Rating rating : allRatings) {
            ratingsByDestination
                    .computeIfAbsent(rating.getDestination(), k -> new ArrayList<>())
                    .add(rating); // Add the rating to the list of ratings for the destination
        }

        // Add destinations, ratings, and user information to the model for rendering in the view
        model.addAttribute("allDestinations", allDestinations);
        model.addAttribute("ratingsByDestination", ratingsByDestination);
        model.addAttribute("user", user);

        return "ratingManagement"; // Return the view name to display the ratings
    }

    // Handles deleting a rating by its ID
    @PostMapping("/deleteRating/{ratingId}")
    public String deleteRating(@PathVariable Long ratingId) {
        Optional<Rating> ratingOpt = ratingRepository.findById(ratingId);
        if (ratingOpt.isPresent()) {
            Rating rating = ratingOpt.get();
            Destination destination = rating.getDestination();
            User user = rating.getUser();

            // Delete only the selected rating
            ratingRepository.delete(rating);

            // Recalculate the destination's average rating
            var averageRatings = ratingRepository.getAverageRatingByDestination(destination.getId());
            destination.setAverageRating(averageRatings == null ? 0 : averageRatings);
            destinationRepository.save(destination);

            // Update reservations linked to this rating
            List<Reservation> reservations = reservationRepository.findByUser(user);
            for (Reservation reservation : reservations) {
                if (reservation.getDestination().equals(destination) && reservation.getDestinationRating() == rating.getStars()) {
                    reservation.setDestinationRating(0);  // Reset the rating for the reservation
                    reservation.setComment(""); // Clear the comment
                    reservationRepository.save(reservation);
                }
            }
        }

        return "redirect:/ratingManagement"; // Redirect back to the rating management page
    }
}
