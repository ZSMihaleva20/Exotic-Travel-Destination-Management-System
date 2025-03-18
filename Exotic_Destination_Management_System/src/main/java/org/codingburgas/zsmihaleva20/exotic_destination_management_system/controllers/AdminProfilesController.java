package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminProfilesController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AdminProfilesController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profilesManagement")
    public String profilesManagement(Model model) {
        model.addAttribute("user", new User());

        // Fetch all users and filter those with roles ADMIN or MANAGER
        List<User> adminAndManagers = userRepository.findAll().stream()
                .filter(user -> "ADMIN".equals(user.getRole()) || "MANAGER".equals(user.getRole()))
                .collect(Collectors.toList());

        model.addAttribute("adminAndManagers", adminAndManagers);

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


    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId) {
        userService.deleteUser(userId);
        return "redirect:/profilesManagement";
    }
}

