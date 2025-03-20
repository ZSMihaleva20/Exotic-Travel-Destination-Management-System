package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import jakarta.transaction.Transactional;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Promotion;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository; // Repository for managing promotions

    @Autowired
    private DestinationRepository destinationRepository; // Repository for managing destinations

     // Creates a new promotion for a given destination.
     // This method sets the promotion's discount and updates the destination's price.
    @Transactional
    public void createPromotion(Long destinationId, double discountPercentage) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        // Create and save promotion
        Promotion promotion = new Promotion();
        promotion.setDestination(destination);
        promotion.setOldPrice(destination.getPrice());
        promotion.setDiscountPercentage(discountPercentage);

        destination.setOnPromotion(true);

        // Update destination's price
        double newPrice = promotion.getNewPrice();
        destination.setPrice(newPrice);

        promotionRepository.save(promotion);
        destinationRepository.save(destination);
    }

    // Retrieves all active promotions from the database.
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }
}
