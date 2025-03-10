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
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, DestinationRepository destinationRepository, ReservationRepository reservationRepository, PromotionService promotionService, RatingService ratingService) {
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

            userOne.setFirstName("Жасмина");
            userOne.setLastName("Михалева");
            userOne.setEmail("robocrforbulgaria@gmail.com");
            userOne.setPassword(passwordEncoder.encode("zhasmina"));
            userOne.setRole("USER");
            userOne.setUsername("zhasmina");

            userRepository.save(userOne);

            admin.setFirstName("Иван");
            admin.setLastName("Василев");
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            admin.setUsername("admin");

            userRepository.save(admin);

            manager.setFirstName("Мартин");
            manager.setLastName("Георгиев");
            manager.setEmail("manager@email.com");
            manager.setPassword(passwordEncoder.encode("manager"));
            manager.setRole("MANAGER");
            manager.setUsername("manager");

            userRepository.save(manager);

            // Дестинация 1 - Малдиви
            destinationOne.setName("Райските острови на Малдиви");
            destinationOne.setDescription(
                    "Малдивите са известни със своите бели плажове, кристално чисти води и луксозни курорти.\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 13 март в 10:00 ч.\n" +
                            "Кацане на Международно летище Мале (MLE) в 18:00 ч.\n" +
                            "Настаняване: Conrad Maldives Rangali Island, луксозен курорт с вили над водата.\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки).\n" +
                            "Активности: Гмуркане с шнорхел, скуба дайвинг, риболов, водни спортове.\n" +
                            "Екскурзии: Посещение на близки острови, вечери на плажа с местни специалитети.\n" +
                            "Допълнително: Спа процедури, йога на плажа, частни вечери."
            );
            destinationOne.setImageUrl("photoTwo.jpg");
            destinationOne.setPrice(1000);
            destinationOne.setStatus("ACCEPTED");
            destinationOne.setLimitedPeople(28);
            destinationOne.setRemainingPeople(28);
            destinationOne.setDateOfDeparture(LocalDate.parse("2025-03-13"));
            destinationOne.setDateOfReturn(LocalDate.parse("2025-03-28"));

            destinationRepository.save(destinationOne);

            // Дестинация 2 - Мексико
            destinationTwo.setName("Тайните на Мексико: Чичен Ица и Карибите");
            destinationTwo.setDescription(
                    "Мексико е земя на древни цивилизации, красиви плажове и вкусна кухня.\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 10 януари в 08:00 ч.\n" +
                            "Кацане на Международно летище Канкун (CUN) в 16:00 ч.\n" +
                            "Настаняване: Hyatt Ziva Cancun, луксозен курорт с изглед към Карибско море.\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки).\n" +
                            "Активности: Плажни игри, водни спортове, вечерни шоута.\n" +
                            "Екскурзии: Посещение на древните пирамиди на Чичен Ица, подводен музей, рибарски села.\n" +
                            "Допълнително: Вечери с традиционна мексиканска кухня, текила тейстънг."
            );
            destinationTwo.setImageUrl("photoOne.jpg");
            destinationTwo.setPrice(2000);
            destinationTwo.setStatus("ACCEPTED");
            destinationTwo.setLimitedPeople(20);
            destinationTwo.setRemainingPeople(20);
            destinationTwo.setDateOfDeparture(LocalDate.parse("2025-01-10"));
            destinationTwo.setDateOfReturn(LocalDate.parse("2025-01-16"));

            destinationRepository.save(destinationTwo);

            // Дестинация 3 - Мароко
            destinationThree.setName("Магията на Мароко: Пустинята и пазарите");
            destinationThree.setDescription(
                    "Мароко е страна на контрасти – от пустинята Сахара до оживените пазари в Марракеш.\n" +
                            "Самолетни битери: Излитане от Летище София (SOF) на 12 март в 09:00 ч.\n" +
                            "Кацане на Летище Марракеш (RAK) в 14:00 ч.\n" +
                            "Настаняване: Royal Mansour Marrakech, луксозен хотел в сърцето на Марракеш.\n" +
                            "Хранене: Полупансион (закуска и вечеря).\n" +
                            "Активности: Разходки с камили в пустинята, катерене в Атласките планини.\n" +
                            "Екскурзии: Посещение на градовете Фес и Мекнес, пазари в Марракеш.\n" +
                            "Допълнително: Вечери с традиционна музика и танци, ароматен маррокански чай."
            );
            destinationThree.setImageUrl("photoThree.jpg");
            destinationThree.setPrice(1500);
            destinationThree.setStatus("ACCEPTED");
            destinationThree.setLimitedPeople(15);
            destinationThree.setRemainingPeople(15);
            destinationThree.setDateOfDeparture(LocalDate.parse("2025-03-12"));
            destinationThree.setDateOfReturn(LocalDate.parse("2025-03-19"));

            destinationRepository.save(destinationThree);

            // Дестинация 4 - Египет
            destinationFour.setName("Тайните на Древен Египет: Пирамиди и Нил");
            destinationFour.setDescription(
                    "Изпитайте чудото на древен Египет с пирамидите, Великата сфинкса и Долината на царете.\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 1 юни в 07:00 ч.\n" +
                            "Кацане на Летище Луксор (LXR) в 12:00 ч.\n" +
                            "Настаняване: Luxor Royal Hotel, хотел с изглед към река Нил.\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки).\n" +
                            "Активности: Разходки с камили, круизи по река Нил.\n" +
                            "Екскурзии: Посещение на пирамидите в Гиза, Египетски музей, храмове в Луксор.\n" +
                            "Допълнително: Вечери с традиционна египетска кухня, светлинно шоу при пирамидите."
            );
            destinationFour.setImageUrl("photoFour.jpg");
            destinationFour.setPrice(1200);
            destinationFour.setStatus("ACCEPTED");
            destinationFour.setLimitedPeople(25);
            destinationFour.setRemainingPeople(25);
            destinationFour.setDateOfDeparture(LocalDate.parse("2025-06-01"));
            destinationFour.setDateOfReturn(LocalDate.parse("2025-06-10"));

            destinationRepository.save(destinationFour);

            // Дестинация 5 - Япония
            destinationFive.setName("Загадките на Япония: Токио и Фуджи");
            destinationFive.setDescription(
                    "Потопете се в богатата култура на Япония – от оживен Токио до спокойна Киото.\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 15 юли в 11:00 ч.\n" +
                            "Кацане на Летище Нарита (NRT) в 20:00 ч.\n" +
                            "Настаняване: Park Hotel Tokyo, модерен хотел в центъра на Токио.\n" +
                            "Хранене: Закуска и вечеря.\n" +
                            "Активности: Посещение на Токио Дисниленд, шопинг в квартал Гинза.\n" +
                            "Екскурзии: Храмове в Киото, планина Фуджи, традиционна чайна церемония.\n" +
                            "Допълнително: Вечери с суши и сашими, представление на гейши."
            );
            destinationFive.setImageUrl("photoFive.jpg");
            destinationFive.setPrice(1800);
            destinationFive.setStatus("ACCEPTED");
            destinationFive.setLimitedPeople(30);
            destinationFive.setRemainingPeople(30);
            destinationFive.setDateOfDeparture(LocalDate.parse("2025-07-15"));
            destinationFive.setDateOfReturn(LocalDate.parse("2025-07-25"));

            destinationRepository.save(destinationFive);

            // Дестинация 6 - Австралия
            destinationSix.setName("Дивата природа на Австралия: Рифове и джунгли");
            destinationSix.setDescription(
                    "Открийте чудесата на Австралия – от Големия бариерен риф до Австралийското вътрешно.\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 1 август в 09:00 ч.\n" +
                            "Кацане на Летище Сидни (SYD) в 22:00 ч.\n" +
                            "Настаняване: Shangri-La Hotel Sydney, хотел с изглед към залива.\n" +
                            "Хранене: Закуска и вечеря.\n" +
                            "Активности: Гмуркане в Големия бариерен риф, сафари в национални паркове.\n" +
                            "Екскурзии: Посещение на Сидни и Мелбърн, Оперен театър в Сидни.\n" +
                            "Допълнително: Вечери с австралийска кухня, обиколки с хеликоптер."
            );
            destinationSix.setImageUrl("photoSix.jpg");
            destinationSix.setPrice(2500);
            destinationSix.setStatus("ACCEPTED");
            destinationSix.setLimitedPeople(35);
            destinationSix.setRemainingPeople(35);
            destinationSix.setDateOfDeparture(LocalDate.parse("2025-08-01"));
            destinationSix.setDateOfReturn(LocalDate.parse("2025-08-15"));

            destinationRepository.save(destinationSix);

            // Дестинация 7 - Тайланд
            destinationSeven.setName("Тропическият рай на Тайланд: Плажове и храмове");
            destinationSeven.setDescription(
                    "Избягайте в тропическия рай на Тайланд с красиви плажове и богата история.\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 5 септември в 08:00 ч.\n" +
                            "Кацане на Летище Пукет (HKT) в 18:00 ч.\n" +
                            "Настаняване: Banyan Tree Phuket, луксозен курорт с частни вили.\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки).\n" +
                            "Активности: Тайландски масажи, водни спортове, плажни игри.\n" +
                            "Екскурзии: Посещение на островите Пхи Пхи, храмове в Банкок.\n" +
                            "Допълнително: Курсове по тайландска кухня, вечерни шоута."
            );
            destinationSeven.setImageUrl("photoSeven.jpg");
            destinationSeven.setPrice(1500);
            destinationSeven.setStatus("ACCEPTED");
            destinationSeven.setLimitedPeople(40);
            destinationSeven.setRemainingPeople(40);
            destinationSeven.setDateOfDeparture(LocalDate.parse("2025-09-05"));
            destinationSeven.setDateOfReturn(LocalDate.parse("2025-09-12"));

            destinationRepository.save(destinationSeven);

            // Дестинация 8 - Бразилия
            destinationEight.setName("Карнавалът на Бразилия: Рио и Амазонка");
            destinationEight.setDescription(
                    "Посетете оживените градове Рио де Жанейро и Сао Пауло, изследвайте Амазонската джунгла.\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 10 октомври в 10:00 ч.\n" +
                            "Кацане на Летище Рио де Жанейро (GIG) в 20:00 ч.\n" +
                            "Настаняване: Copacabana Palace, луксозен хотел на плажа Копакабана.\n" +
                            "Хранене: Закуска и вечеря.\n" +
                            "Активности: Плажни игри, катерене до статуята Христос Спасител.\n" +
                            "Екскурзии: Посещение на Амазонската джунгла, град Сао Пауло.\n" +
                            "Допълнително: Вечери с бразилска кухня, обиколки с джип."
            );
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