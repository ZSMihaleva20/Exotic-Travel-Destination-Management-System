package org.codingburgas.zsmihaleva20.exotic_destination_management_system.utils;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, DestinationRepository destinationRepository) {
        return args -> {
            User userOne = new User();
            User admin = new User();
            User manager = new User();

            Destination destination = new Destination();

            userOne.setFirstName("Zhasmina");
            userOne.setLastName("Mihaleva");
            userOne.setEmail("jasminamihaleva2006@gmail.com");
            userOne.setPassword(passwordEncoder.encode("zhasmina"));
            userOne.setRole("USER");
            userOne.setUsername("zhasmina");

            userRepository.save(userOne);

            admin.setFirstName("Admin");
            admin.setLastName("Adminov");
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            admin.setUsername("admin");

            userRepository.save(admin);

            manager.setFirstName("Manager");
            manager.setLastName("Managerov");
            manager.setEmail("manager@email.com");
            manager.setPassword(passwordEncoder.encode("manager"));
            manager.setRole("MANAGER");
            manager.setUsername("manager");

            userRepository.save(manager);

            destination.setName("Turciq");
            destination.setDescription("Destinaciq");
            destination.setImageUrl("photoTwo.jpg");
            destination.setPrice(1000);
            destination.setStatus("ACCEPTED");
            destination.setLimitedPeople(28);
            destination.setRemainingPeople(28);
            destination.setDateOfDeparture(LocalDate.parse("2025-03-25"));
            destination.setDateOfReturn(LocalDate.parse("2025-03-28"));
            destinationRepository.save(destination);
        };
    }
}