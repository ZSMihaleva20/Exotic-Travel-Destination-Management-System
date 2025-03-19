package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Campaign;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Promotion;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.CampaignService;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PromotionAndCampaignController {
    // Injecting services to interact with Promotions and Campaigns
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private CampaignService campaignService;

    // Handles requests to the "/promotionsAndCampaigns" page
    @GetMapping("/promotionsAndCampaigns")
    public String listPromotionsAndCampaigns(@AuthenticationPrincipal User user, Model model) {
        // Retrieve all promotions and campaigns
        List<Promotion> promotions = promotionService.getAllPromotions();
        List<Campaign> campaigns = campaignService.getAllCampaigns();

        // Add attributes to the model for rendering in the view
        model.addAttribute("promotions", promotions);
        model.addAttribute("campaigns", campaigns);
        model.addAttribute("user", user);

        // Return the view name that will display the promotions and campaigns
        return "promotionsAndCampaigns";
    }
}
