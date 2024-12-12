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
import java.util.ArrayList;
import java.util.List;

@Controller
public class DestinationManagementController {
    @Autowired
    private final DestinationService destinationService;

    public DestinationManagementController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/destinationManagement")
    public String destinationManagementList(Model model) {
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        return "destinationManagement";
    }


    @GetMapping("/addDestination")
    public String addDestinationForm(Model model) {
        model.addAttribute("destination", new Destination());
        return "addDestination";
    }

    @PostMapping("/addDestination")
    public String saveDestination(@ModelAttribute Destination destination, @RequestParam("images") MultipartFile[] imageFiles) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    Path uploadDir = Paths.get("uploads");
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }
                    assert fileName != null;
                    Files.copy(file.getInputStream(), uploadDir.resolve(fileName));
                    imageUrls.add(fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        destination.setImageUrls(imageUrls);
        destinationService.saveDestination(destination);
        return "redirect:/destinationManagement";
    }

    @GetMapping("/editDestination/{id}")
    public String editDestinationForm(@PathVariable int id, Model model) {
        model.addAttribute("destination", destinationService.getDestination(id));
        return "editDestination";
    }

    @PostMapping("/editDestination/{id}")
    public String updateDestination(@PathVariable Long id, @ModelAttribute Destination destinationDetails, @RequestParam("images") MultipartFile[] imageFiles) {
        Destination destination = destinationService.getDestination(id);
        destination.setName(destinationDetails.getName());
        destination.setDescription(destinationDetails.getDescription());
        destination.setPrice(destinationDetails.getPrice());

        List<String> imageUrls = new ArrayList<>(destination.getImageUrls());
        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    Path uploadDir = Paths.get("uploads");
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }
                    Files.copy(file.getInputStream(), uploadDir.resolve(fileName));
                    imageUrls.add(fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        destination.setImageUrls(imageUrls);
        destinationService.saveDestination(destination);
        return "redirect:/destinationManagement";
    }


    @PostMapping("/delete/{id}")
    public String deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return "redirect:/destinationManagement";
    }
}
