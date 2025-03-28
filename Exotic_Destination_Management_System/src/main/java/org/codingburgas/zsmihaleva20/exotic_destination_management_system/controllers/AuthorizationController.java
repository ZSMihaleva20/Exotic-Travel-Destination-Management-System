package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.config.SecurityConfig;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorizationController {

    // Declare dependencies for security configuration and user service
    private final SecurityConfig securityConfig;
    private final UserService userService;

    // Constructor-based dependency injection for SecurityConfig and UserService
    @Autowired
    public AuthorizationController(SecurityConfig securityConfig, UserService userService) {
        this.securityConfig = securityConfig;
        this.userService = userService;
    }

    // Handles POST requests for user registration at /register
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Потребителското име вече съществува.");
            return "register";
        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Има съществуващ акаунт с този имейл.");
            return "register";
        }

        user.setRole("USER");
        userService.createUser(user);
        return "redirect:/login";
    }


    // Handles GET requests for the homepage ("/")
    @GetMapping("/")
    public String index() {
        return "home";
    }

    // Handles GET requests for the registration page ("/register")
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handles GET requests for logging out the user
    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    // Handles GET requests for the login page ("/login")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Handles GET requests for the user's profile page ("/profile")
    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }

}
