package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DestinationManagementController {
    @Autowired
    private final DestinationService destinationService;

    public DestinationManagementController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/destinationManagement")
    public String destinationManagementList(Model model) {
        model.addAttribute("destinations", destinationService.getAllDestinations());
        return "destinationManagement";
    }

    @GetMapping("/addDestination")
    public String addDestinationForm(Model model) {
        model.addAttribute("destination", new Destination());
        return "addDestination";
    }

    @PostMapping("/addDestination")
    public String saveDestination(@ModelAttribute Destination destination, @RequestParam("image") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("photos").resolve(fileName);
            if (!Files.exists(uploadPath)) {
                throw new RuntimeException("File not found in photos directory: " + fileName);
            }
            destination.setImageUrl(fileName);
        }
        destinationService.saveDestination(destination);
        return "redirect:/destinationManagement";
    }

    @GetMapping("/editDestination/{id}")
    public String editDestinationForm(@PathVariable Long id, Model model) {
        model.addAttribute("destination", destinationService.getDestination(id));
        return "editDestination";
    }

    @PostMapping("/editDestination/{id}")
    public String updateDestination(@PathVariable Long id, @ModelAttribute Destination destinationDetails, @RequestParam("image") MultipartFile file) {
        Destination destination = destinationService.getDestination(id);
        destination.setName(destinationDetails.getName());
        destination.setDescription(destinationDetails.getDescription());
        destination.setPrice(destinationDetails.getPrice());
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("photos", fileName);
            if (!Files.exists(uploadPath)) {
                throw new RuntimeException("File not found in photos directory: " + fileName);
            }
            destination.setImageUrl(fileName);
        }
        destinationService.saveDestination(destination);
        return "redirect:/destinationManagement";
    }

    @PostMapping("/delete/{id}")
    public String deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return "redirect:/destinationManagement";
    }
}

