package org.codingburgas.zsmihaleva20.exotic_destination_management_system.utils;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, DestinationRepository destinationRepository, ReservationRepository reservationRepository) {
        return args -> {
            User userOne = new User();
            User admin = new User();
            User manager = new User();

            Destination destinationOne = new Destination();
            Destination destinationTwo = new Destination();
            Destination destinationThree = new Destination();

            Reservation reservationOne = new Reservation();
            Reservation reservationTwo = new Reservation();
            Reservation reservationThree = new Reservation();
            Reservation reservationFour = new Reservation();
            Reservation reservationFive = new Reservation();
            Reservation reservationSix = new Reservation();

            userOne.setFirstName("Zhasmina");
            userOne.setLastName("Mihaleva");
            userOne.setEmail("robocrforbulgaria@gmail.com");
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

            destinationOne.setName("Turciq");
            destinationOne.setDescription("Destinaciq");
            destinationOne.setImageUrl("photoTwo.jpg");
            destinationOne.setPrice(1000);
            destinationOne.setStatus("ACCEPTED");
            destinationOne.setLimitedPeople(28);
            destinationOne.setRemainingPeople(28);
            destinationOne.setDateOfDeparture(LocalDate.parse("2025-03-25"));
            destinationOne.setDateOfReturn(LocalDate.parse("2025-03-28"));
            destinationRepository.save(destinationOne);

            destinationTwo.setName("Gurciq");
            destinationTwo.setDescription("Destinaciq");
            destinationTwo.setImageUrl("photoOne.jpg");
            destinationTwo.setPrice(200);
            destinationTwo.setStatus("ACCEPTED");
            destinationTwo.setLimitedPeople(20);
            destinationTwo.setRemainingPeople(20);
            destinationTwo.setDateOfDeparture(LocalDate.parse("2025-04-10"));
            destinationTwo.setDateOfReturn(LocalDate.parse("2025-04-16"));
            destinationRepository.save(destinationTwo);

            destinationThree.setName("Albania");
            destinationThree.setDescription("Destinaciq");
            destinationThree.setImageUrl("photoThree.jpg");
            destinationThree.setPrice(500);
            destinationThree.setStatus("ACCEPTED");
            destinationThree.setLimitedPeople(15);
            destinationThree.setRemainingPeople(15);
            destinationThree.setDateOfDeparture(LocalDate.parse("2025-05-12"));
            destinationThree.setDateOfReturn(LocalDate.parse("2025-05-15"));
            destinationRepository.save(destinationThree);

            reservationOne.setDestination(destinationOne);
            reservationOne.setUser(userOne);
            reservationOne.setNumberOfPeople(5);
            reservationOne.setDestinationRated(false);
            reservationOne.setTotalPrice(10000);
            reservationOne.setStatus("BOOKED");

            reservationRepository.save(reservationOne);

            reservationTwo.setDestination(destinationTwo);
            reservationTwo.setUser(userOne);
            reservationTwo.setNumberOfPeople(7);
            reservationTwo.setDestinationRated(false);
            reservationTwo.setTotalPrice(344);
            reservationTwo.setStatus("BOOKED");

            reservationRepository.save(reservationTwo);

            reservationThree.setDestination(destinationThree);
            reservationThree.setUser(userOne);
            reservationThree.setNumberOfPeople(3);
            reservationThree.setDestinationRated(false);
            reservationThree.setTotalPrice(1500);
            reservationThree.setStatus("BOOKED");

            reservationRepository.save(reservationThree);

            reservationFour.setDestination(destinationOne);
            reservationFour.setUser(userOne);
            reservationFour.setNumberOfPeople(2);
            reservationFour.setDestinationRated(false);
            reservationFour.setTotalPrice(1500);
            reservationFour.setStatus("BOOKED");

            reservationRepository.save(reservationFour);

            reservationFive.setDestination(destinationTwo);
            reservationFive.setUser(userOne);
            reservationFive.setNumberOfPeople(7);
            reservationFive.setDestinationRated(false);
            reservationFive.setTotalPrice(1400);
            reservationFive.setStatus("BOOKED");

            reservationRepository.save(reservationFive);

            reservationSix.setDestination(destinationThree);
            reservationSix.setUser(userOne);
            reservationSix.setNumberOfPeople(5);
            reservationSix.setDestinationRated(false);
            reservationSix.setTotalPrice(1500);
            reservationSix.setStatus("BOOKED");

            reservationRepository.save(reservationSix);
        };
    }
}