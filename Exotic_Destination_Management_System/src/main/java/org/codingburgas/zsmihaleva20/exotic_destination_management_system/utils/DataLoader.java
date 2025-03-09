package org.codingburgas.zsmihaleva20.exotic_destination_management_system.utils;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.*;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.*;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.PromotionService;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.RatingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, DestinationRepository destinationRepository, ReservationRepository reservationRepository, RatingRepository ratingRepository, PromotionService promotionService, RatingService ratingService) {
        return args -> {
            User userOne = new User();
            User admin = new User();
            User manager = new User();

            Destination destinationOne = new Destination();
            Destination destinationTwo = new Destination();
            Destination destinationThree = new Destination();
            Destination destinationFour = new Destination();
            Destination destinationFive = new Destination();
            Destination destinationSix = new Destination();
            Destination destinationSeven = new Destination();
            Destination destinationEight = new Destination();

            Reservation reservationOne = new Reservation();
            Reservation reservationTwo = new Reservation();
            Reservation reservationThree = new Reservation();
            Reservation reservationFour = new Reservation();
            Reservation reservationFive = new Reservation();
            Reservation reservationSix = new Reservation();

            Rating ratingOne = new Rating();

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
            destinationOne.setDescription("Насладете се на уникалната турска култура, вкусна храна и красиви плажове. " +
                    "Пакетът включва самолетни билети, настаняване в Istanbul Grand Hotel, както и закуска и вечеря. " +
                    "Очакват ви незабравими преживявания като обиколка на историческите забележителности и разходка с лодка по Босфора.");
            destinationOne.setImageUrl("photoTwo.jpg");
            destinationOne.setPrice(1000);
            destinationOne.setStatus("ACCEPTED");
            destinationOne.setLimitedPeople(28);
            destinationOne.setRemainingPeople(28);
            destinationOne.setDateOfDeparture(LocalDate.parse("2025-03-25"));
            destinationOne.setDateOfReturn(LocalDate.parse("2025-03-28"));

            destinationRepository.save(destinationOne);

            destinationTwo.setName("Gurciq");
            destinationTwo.setDescription("Райски острови с бели плажове, кристално синя вода и луксозни курорти. " +
                    "Пакетът включва самолетни билети, настаняване в Maldives Paradise Resort и All-Inclusive изхранване (закуска, обяд, вечеря). " +
                    "Ще можете да се насладите на гмуркане с шнорхел, разходка с яхта и спа процедури.");
            destinationTwo.setImageUrl("photoOne.jpg");
            destinationTwo.setPrice(200);
            destinationTwo.setStatus("ACCEPTED");
            destinationTwo.setLimitedPeople(20);
            destinationTwo.setRemainingPeople(20);
            destinationTwo.setDateOfDeparture(LocalDate.parse("2025-01-10"));
            destinationTwo.setDateOfReturn(LocalDate.parse("2025-01-16"));
            destinationRepository.save(destinationTwo);

            destinationThree.setName("Albania");
            destinationThree.setDescription("Потопете се в италианската култура, насладете се на пица, паста и невероятна архитектура. " +
                    "Пакетът включва самолетни билети и настаняване в Rome Luxury Hotel. " +
                    "Храната не е включена, за да имате свободата да опитате местни ресторанти. " +
                    "Посетете Колизеума, Ватикана и Фонтан ди Треви.");
            destinationThree.setImageUrl("photoThree.jpg");
            destinationThree.setPrice(500);
            destinationThree.setStatus("ACCEPTED");
            destinationThree.setLimitedPeople(15);
            destinationThree.setRemainingPeople(15);
            destinationThree.setDateOfDeparture(LocalDate.parse("2025-05-12"));
            destinationThree.setDateOfReturn(LocalDate.parse("2025-05-15"));
            destinationRepository.save(destinationThree);


            destinationFour.setName("Egypt");
            destinationFour.setDescription("Experience the wonder of ancient Egypt. Explore the pyramids, the Great Sphinx, and the Valley of the Kings. " +
                    "This package includes round-trip flights, a stay at the Luxor Royal Hotel, and guided tours to iconic sites. " +
                    "Relax by the Nile and enjoy traditional Egyptian cuisine.");
            destinationFour.setImageUrl("photoFour.jpg");
            destinationFour.setPrice(1200);
            destinationFour.setStatus("ACCEPTED");
            destinationFour.setLimitedPeople(25);
            destinationFour.setRemainingPeople(25);
            destinationFour.setDateOfDeparture(LocalDate.parse("2025-06-01"));
            destinationFour.setDateOfReturn(LocalDate.parse("2025-06-10"));
            destinationRepository.save(destinationFour);

            // Destination 5 - Japan

            destinationFive.setName("Japan");
            destinationFive.setDescription("Immerse yourself in the rich culture of Japan. From bustling Tokyo to serene Kyoto, experience the best of both worlds. " +
                    "The package includes flights, hotel stays, and a guided tour to visit the temples, Mount Fuji, and Tokyo Disneyland. " +
                    "Enjoy sushi, ramen, and the beauty of cherry blossoms during your stay.");
            destinationFive.setImageUrl("photoFive.jpg");
            destinationFive.setPrice(1800);
            destinationFive.setStatus("ACCEPTED");
            destinationFive.setLimitedPeople(30);
            destinationFive.setRemainingPeople(30);
            destinationFive.setDateOfDeparture(LocalDate.parse("2025-07-15"));
            destinationFive.setDateOfReturn(LocalDate.parse("2025-07-25"));
            destinationRepository.save(destinationFive);

            // Destination 6 - Australia

            destinationSix.setName("Australia");
            destinationSix.setDescription("Discover the wonders of Australia – from the Great Barrier Reef to the Outback. This package includes flights, accommodations at a 5-star resort, and snorkeling tours. " +
                    "Explore the vibrant cities of Sydney and Melbourne, and enjoy the amazing wildlife and nature of the land down under.");
            destinationSix.setImageUrl("photoSix.jpg");
            destinationSix.setPrice(2500);
            destinationSix.setStatus("ACCEPTED");
            destinationSix.setLimitedPeople(35);
            destinationSix.setRemainingPeople(35);
            destinationSix.setDateOfDeparture(LocalDate.parse("2025-08-01"));
            destinationSix.setDateOfReturn(LocalDate.parse("2025-08-15"));
            destinationRepository.save(destinationSix);

            // Destination 7 - Thailand

            destinationSeven.setName("Thailand");
            destinationSeven.setDescription("Escape to the tropical paradise of Thailand. Enjoy beautiful beaches, vibrant markets, and rich history. " +
                    "This package includes flights, stays at luxury resorts in Phuket and Bangkok, and cultural tours. " +
                    "Indulge in traditional Thai massages and explore ancient temples.");
            destinationSeven.setImageUrl("photoSeven.jpg");
            destinationSeven.setPrice(1500);
            destinationSeven.setStatus("ACCEPTED");
            destinationSeven.setLimitedPeople(40);
            destinationSeven.setRemainingPeople(40);
            destinationSeven.setDateOfDeparture(LocalDate.parse("2025-09-05"));
            destinationSeven.setDateOfReturn(LocalDate.parse("2025-09-12"));
            destinationRepository.save(destinationSeven);

            // Destination 8 - Brazil

            destinationEight.setName("Brazil");
            destinationEight.setDescription("Visit the vibrant cities of Rio de Janeiro and São Paulo, explore the Amazon rainforest, and enjoy world-class beaches. " +
                    "The package includes flights, hotel accommodations, and an unforgettable experience during the Carnival season in Rio.");
            destinationEight.setImageUrl("photoEight.jpg");
            destinationEight.setPrice(2200);
            destinationEight.setStatus("ACCEPTED");
            destinationEight.setLimitedPeople(20);
            destinationEight.setRemainingPeople(20);
            destinationEight.setDateOfDeparture(LocalDate.parse("2025-10-10"));
            destinationEight.setDateOfReturn(LocalDate.parse("2025-10-20"));
            destinationRepository.save(destinationEight);

            reservationOne.setDestination(destinationOne);
            reservationOne.setUser(userOne);
            reservationOne.setNumberOfPeople(5);
            reservationOne.setDestinationRated(true);
            reservationOne.setTotalPrice(10000);
            reservationOne.setStatus("BOOKED");
            destinationOne.setRemainingPeople(destinationOne.getRemainingPeople() - reservationOne.getNumberOfPeople());
            destinationOne.setPopularity(destinationOne.getPopularity() + reservationOne.getNumberOfPeople());

            reservationRepository.save(reservationOne);
            destinationRepository.save(destinationOne);

            reservationTwo.setDestination(destinationTwo);
            reservationTwo.setUser(userOne);
            reservationTwo.setNumberOfPeople(7);
            reservationTwo.setDestinationRated(false);
            reservationTwo.setTotalPrice(344);
            reservationTwo.setStatus("BOOKED");
            destinationTwo.setRemainingPeople(destinationTwo.getRemainingPeople() - reservationTwo.getNumberOfPeople());
            destinationTwo.setPopularity(destinationTwo.getPopularity() + reservationTwo.getNumberOfPeople());

            reservationRepository.save(reservationTwo);
            destinationRepository.save(destinationTwo);

            reservationThree.setDestination(destinationThree);
            reservationThree.setUser(userOne);
            reservationThree.setNumberOfPeople(3);
            reservationThree.setDestinationRated(false);
            reservationThree.setTotalPrice(1500);
            reservationThree.setStatus("BOOKED");
            destinationThree.setRemainingPeople(destinationThree.getRemainingPeople() - reservationThree.getNumberOfPeople());
            destinationThree.setPopularity(destinationThree.getPopularity() + reservationThree.getNumberOfPeople());

            reservationRepository.save(reservationThree);
            destinationRepository.save(destinationThree);

            reservationFour.setDestination(destinationOne);
            reservationFour.setUser(userOne);
            reservationFour.setNumberOfPeople(2);
            reservationFour.setDestinationRated(false);
            reservationFour.setTotalPrice(1500);
            reservationFour.setStatus("BOOKED");
            destinationOne.setRemainingPeople(destinationOne.getRemainingPeople() - reservationFour.getNumberOfPeople());
            destinationOne.setPopularity(destinationOne.getPopularity() + reservationFour.getNumberOfPeople());

            reservationRepository.save(reservationFour);
            destinationRepository.save(destinationOne);

            reservationFive.setDestination(destinationTwo);
            reservationFive.setUser(userOne);
            reservationFive.setNumberOfPeople(7);
            reservationFive.setDestinationRated(false);
            reservationFive.setTotalPrice(1400);
            reservationFive.setStatus("BOOKED");
            destinationTwo.setRemainingPeople(destinationTwo.getRemainingPeople() - reservationFive.getNumberOfPeople());
            destinationTwo.setPopularity(destinationTwo.getPopularity() + reservationFive.getNumberOfPeople());

            reservationRepository.save(reservationFive);
            destinationRepository.save(destinationTwo);

            reservationSix.setDestination(destinationThree);
            reservationSix.setUser(userOne);
            reservationSix.setNumberOfPeople(5);
            reservationSix.setDestinationRated(false);
            reservationSix.setTotalPrice(1500);
            reservationSix.setStatus("BOOKED");
            destinationThree.setRemainingPeople(destinationThree.getRemainingPeople() - reservationSix.getNumberOfPeople());
            destinationThree.setPopularity(destinationThree.getPopularity() + reservationSix.getNumberOfPeople());

            reservationRepository.save(reservationSix);
            destinationRepository.save(destinationThree);

            promotionService.createPromotion(destinationSeven.getId(), 20.00);
            promotionService.createPromotion(destinationSix.getId(), 10.00);
            promotionService.createPromotion(destinationEight.getId(), 15.00);


            ratingOne.setStars(5);
            ratingOne.setDestination(destinationOne);
            ratingOne.setComment("Чудесно обслужване");

            ratingOne.setUser(userOne);

            reservationOne.setDestinationRating(ratingOne.getStars());
            reservationOne.setComment(ratingOne.getComment());

            reservationRepository.save(reservationOne);


            ratingService.saveRating(ratingOne);
        };
    }
}