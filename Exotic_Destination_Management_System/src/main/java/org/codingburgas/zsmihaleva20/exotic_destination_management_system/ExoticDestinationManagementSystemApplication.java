package org.codingburgas.zsmihaleva20.exotic_destination_management_system;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class    ExoticDestinationManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExoticDestinationManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, DestinationRepository destinationRepository) {
        return args -> {
            User userOne = new User();
            Destination destination = new Destination();

            userOne.setFirstName("John");
            userOne.setLastName("Smith");
            userOne.setEmail("john.smith@gmail.com");
            userOne.setPassword(passwordEncoder.encode("password"));
            userOne.setRole("USER");
            userOne.setUsername("john");

            userRepository.save(userOne);

            destination.setName("Turciq");
            destination.setDescription("Destinaciq");
            destination.setImageUrl("photoTwo.jpg");
            destination.setPrice(1000);
            destination.setStatus("ACCEPTED");
            destination.setLimitedPeople(28);
            destinationRepository.save(destination);
        };
    }

}
