package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import jakarta.transaction.Transactional;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Campaign;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.CampaignRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    // Create a campaign with a discount
    @Transactional
    public void createCampaign(Long destinationId, double discountPercentage) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Campaign campaign = new Campaign();
        campaign.setDestination(destination);
        campaign.setOldPrice(destination.getPrice());
        campaign.setDiscountPercentage(discountPercentage);

        // Set destination as on promotion
        destination.setOnPromotion(true);

        // Calculate new price
        double newPrice = campaign.getNewPrice();
        destination.setPrice(newPrice);

        campaignRepository.save(campaign);
        destinationRepository.save(destination);
    }

    // Return all campaigns
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }
}
