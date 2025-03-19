package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.StatisticService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    private final StatisticService statisticService;

    // Constructor injection of the StatisticService
    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    // Method to display statistics page with user-related data
    @GetMapping("/statistics")
    public String showStatistics(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("destinationRatings", statisticService.getDestinationRatings());
        model.addAttribute("destinationPopularity", statisticService.getDestinationPopularity());
        return "statistics";
    }
}
