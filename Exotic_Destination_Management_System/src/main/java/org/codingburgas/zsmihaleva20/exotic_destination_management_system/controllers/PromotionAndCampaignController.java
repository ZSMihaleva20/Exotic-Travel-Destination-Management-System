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
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/promotionsAndCampaigns")
    public String listPromotionsAndCampaigns(@AuthenticationPrincipal User user, Model model) {
        List<Promotion> promotions = promotionService.getAllPromotions();
        List<Campaign> campaigns = campaignService.getAllCampaigns();

        model.addAttribute("promotions", promotions);
        model.addAttribute("campaigns", campaigns);
        model.addAttribute("user", user);
        return "promotionsAndCampaigns";
    }
}
