package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminProfilesController {
    @GetMapping("/adminProfiles")
    public String logInAdminProfiles() {
        return "adminProfiles";
    }
}

