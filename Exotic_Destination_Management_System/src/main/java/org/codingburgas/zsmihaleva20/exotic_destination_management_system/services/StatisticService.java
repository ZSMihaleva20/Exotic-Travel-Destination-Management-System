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

    public StatisticService(DestinationRepository destinationRepository, RatingRepository ratingRepository) {
        this.destinationRepository = destinationRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<DestinationRatingDto> getDestinationRatings() {
        return destinationRepository.findAll().stream().map(d -> {
            var dto = new DestinationRatingDto();
            dto.setId(d.getId());
            dto.setRating(d.getAverageRating());
            dto.setName(d.getName());
            return dto;
        }).toList();
    }
}
