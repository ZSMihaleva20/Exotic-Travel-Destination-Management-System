package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminProfilesController {
    private final UserService userService;

    @Autowired
    public AdminProfilesController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profilesManagement")
    public String profilesManagement(Model model) {
        model.addAttribute("user", new User());
        return "profilesManagement";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        userService.createUser(user);
        return "redirect:/profilesManagement";
    }

}

