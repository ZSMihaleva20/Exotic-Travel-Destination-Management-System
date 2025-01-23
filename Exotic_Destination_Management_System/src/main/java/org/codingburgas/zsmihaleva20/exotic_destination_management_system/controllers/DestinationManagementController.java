package org.codingburgas.zsmihaleva20.exotic_destination_management_system.controllers;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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

    @GetMapping(value = "/uploaded-image/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable String name) throws IOException {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        if (name.endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        }

        Path uploadPath = Paths.get("photos").resolve(name);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(uploadPath));
        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }

    @PostMapping("/addDestination")
    public String saveDestination(@ModelAttribute Destination destination, @RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("photos").resolve(fileName);
            if (!Files.exists(uploadPath)) {
                Files.write(uploadPath, file.getBytes());
                //throw new RuntimeException("File not found in photos directory: " + fileName);
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
    public String updateDestination(@PathVariable Long id, @ModelAttribute Destination destinationDetails, @RequestParam("image") MultipartFile file) throws IOException {
        Destination destination = destinationService.getDestination(id);
        destination.setName(destinationDetails.getName());
        destination.setDescription(destinationDetails.getDescription());
        destination.setPrice(destinationDetails.getPrice());
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("photos", fileName);
            if (!Files.exists(uploadPath)) {
                Files.write(uploadPath, file.getBytes());
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


    /*@PostMapping("/requestEdit/{id}")
    public String requestEditDestination(@PathVariable Long id, @ModelAttribute Destination destinationDetails, @RequestParam("image") MultipartFile file) throws IOException {
        Destination destination = destinationService.getDestination(id);
        destination.setPendingApproval(true);
        destination.setRequestedAction("EDIT");
        destinationService.saveDestination(destination);
        return "redirect:/destinationManagement";
    }*/

    @PostMapping("/requestDelete/{id}")
    public String requestDeleteDestination(@PathVariable Long id) {
        Destination destination = destinationService.getDestination(id);
        destination.setPendingApproval(true);
        destination.setStatus("PENDING-DELETE");
        destinationService.saveDestination(destination);
        return "redirect:/destinationManagement";
    }

    @PostMapping("/approveRequest/{id}")
    public String approveRequest(@PathVariable Long id) {
        Destination destination = destinationService.getDestination(id);
        if (Objects.equals(destination.getStatus(), "PENDING-DELETE")) {
            destinationService.deleteDestination(id);
        }
        else {
            destination.setStatus("ACCEPTED");
            destinationService.saveDestination(destination);
        }
        return "redirect:/destinationManagement";
    }

    @PostMapping("/declineRequest/{id}")
    public String declineRequest(@PathVariable Long id) {
        Destination destination = destinationService.getDestination(id);
        if (Objects.equals(destination.getStatus(), "PENDING-DELETE")) {
            destination.setStatus("ACCEPTED");
            destinationService.saveDestination(destination);
        }
        else {
            destination.setStatus("DECLINED");
            destination.setPendingApproval(false);
            destinationService.deleteDestination(id);
        }
        return "redirect:/destinationManagement";
    }
}

