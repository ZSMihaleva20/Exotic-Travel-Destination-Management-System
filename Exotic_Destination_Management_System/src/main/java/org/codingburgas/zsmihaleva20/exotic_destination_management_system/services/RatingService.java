package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Rating;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final DestinationRepository destinationRepository;

    public RatingService(RatingRepository ratingRepository, DestinationRepository destinationRepository) {
        this.ratingRepository = ratingRepository;
        this.destinationRepository = destinationRepository;
    }

    public Rating saveRating(Rating rating) {
        var destination = rating.getDestination();
        destination.addRating(rating);
        ratingRepository.save(rating);
        destination.setAverageRating(ratingRepository.getAverageRatingByDestination(destination.getId()));
        destinationRepository.save(destination);
        return rating;
    }
}
