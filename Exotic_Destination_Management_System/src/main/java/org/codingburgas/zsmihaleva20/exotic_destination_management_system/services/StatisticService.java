package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.dto.DestinationRatingDto;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    private final DestinationRepository destinationRepository;
    private final RatingRepository ratingRepository;

    // Constructor to inject repositories for destination and rating data
    public StatisticService(DestinationRepository destinationRepository, RatingRepository ratingRepository) {
        this.destinationRepository = destinationRepository;
        this.ratingRepository = ratingRepository;
    }

    // Retrieves a list of destinations with their average ratings
    public List<DestinationRatingDto> getDestinationRatings() {
        return destinationRepository.findAll().stream().map(d -> {
            var dto = new DestinationRatingDto();
            dto.setId(d.getId());
            dto.setRating(d.getAverageRating());
            dto.setName(d.getName());
            return dto;
        }).toList();
    }

    // Retrieves a list of destinations with their popularity information
    public List<DestinationRatingDto> getDestinationPopularity() {
        return destinationRepository.findAll().stream().map(d -> {
            var dto = new DestinationRatingDto();
            dto.setId(d.getId());
            dto.setName(d.getName());
            dto.setPopularity(d.getPopularity()); // Only setting popularity
            return dto;
        }).toList();
    }

}
