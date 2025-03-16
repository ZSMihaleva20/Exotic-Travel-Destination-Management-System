package org.codingburgas.zsmihaleva20.exotic_destination_management_system.utils;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.*;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.*;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.CampaignService;
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
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, DestinationRepository destinationRepository, ReservationRepository reservationRepository, PromotionService promotionService, RatingService ratingService, CampaignService campaignService) {
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
            Destination destinationNine = new Destination();
            Destination destinationTen = new Destination();
            Destination destinationEleven = new Destination();

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
            userOne.setPassword(passwordEncoder.encode("zhasminaMihaleva12!"));
            userOne.setRole("USER");
            userOne.setUsername("zhasmina");

            userRepository.save(userOne);

            admin.setFirstName("Иван");
            admin.setLastName("Василев");
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("adminAdminov12!"));
            admin.setRole("ADMIN");
            admin.setUsername("admin");

            userRepository.save(admin);

            manager.setFirstName("Мартин");
            manager.setLastName("Георгиев");
            manager.setEmail("manager@email.com");
            manager.setPassword(passwordEncoder.encode("managerManagerov12!"));
            manager.setRole("MANAGER");
            manager.setUsername("manager");

            userRepository.save(manager);

            // Дестинация 1 - Малдиви
            destinationOne.setName("Райските острови на Малдиви");
            destinationOne.setDescription(
                    "Малдивите са известни със своите бели плажове, кристално чисти води и луксозни курорти;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 13 март 2025г. в 10:00 ч.;\n" +
                            "Кацане на Международно летище Мале (MLE) в 18:00 ч.;\n" +
                            "Настаняване: Conrad Maldives Rangali Island, луксозен курорт с вили над водата;\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки);\n" +
                            "Активности: Гмуркане с шнорхел, скуба дайвинг, риболов, водни спортове;\n" +
                            "Екскурзии: Посещение на близки острови, вечери на плажа с местни специалитети;\n" +
                            "Допълнително: Спа процедури, йога на плажа, частни вечери"
            );
            destinationOne.setImageUrl("maldivies.jpg");
            destinationOne.setPrice(1000);
            destinationOne.setStatus("ACCEPTED");
            destinationOne.setLimitedPeople(28);
            destinationOne.setRemainingPeople(28);
            destinationOne.setDateOfDeparture(LocalDate.parse("2025-02-14"));
            destinationOne.setDateOfReturn(LocalDate.parse("2025-02-28"));

            destinationRepository.save(destinationOne);

            // Дестинация 2 - Мексико
            destinationTwo.setName("Тайните на Мексико: Чичен Ица и Карибите");
            destinationTwo.setDescription(
                    "Мексико е земя на древни цивилизации, красиви плажове и вкусна кухня;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 10 януари 2025г. в 08:00ч.;\n" +
                            "Кацане на Международно летище Канкун (CUN) в 16:00 ч.;\n" +
                            "Настаняване: Hyatt Ziva Cancun, луксозен курорт с изглед към Карибско море;\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки);\n" +
                            "Активности: Плажни игри, водни спортове, вечерни шоута;\n" +
                            "Екскурзии: Посещение на древните пирамиди на Чичен Ица, подводен музей, рибарски села;\n" +
                            "Допълнително: Вечери с традиционна мексиканска кухня, текила тейстънг"
            );
            destinationTwo.setImageUrl("mexico.jpg");
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
                    "Мароко е страна на контрасти – от пустинята Сахара до оживените пазари в Марракеш;\n" +
                            "Самолетни битери: Излитане от Летище София (SOF) на 12 март 2025г. в 09:00 ч.;\n" +
                            "Кацане на Летище Марракеш (RAK) в 14:00 ч.;\n" +
                            "Настаняване: Royal Mansour Marrakech, луксозен хотел в сърцето на Марракеш;\n" +
                            "Хранене: Полупансион (закуска и вечеря);\n" +
                            "Активности: Разходки с камили в пустинята, катерене в Атласките планини;\n" +
                            "Екскурзии: Посещение на градовете Фес и Мекнес, пазари в Марракеш;\n" +
                            "Допълнително: Вечери с традиционна музика и танци, ароматен маррокански чай"
            );
            destinationThree.setImageUrl("morocco.jpg");
            destinationThree.setPrice(1500);
            destinationThree.setStatus("ACCEPTED");
            destinationThree.setLimitedPeople(15);
            destinationThree.setRemainingPeople(15);
            destinationThree.setDateOfDeparture(LocalDate.parse("2025-03-19"));
            destinationThree.setDateOfReturn(LocalDate.parse("2025-05-19"));

            destinationRepository.save(destinationThree);

            // Дестинация 4 - Египет
            destinationFour.setName("Тайните на Древен Египет: Пирамиди и Нил");
            destinationFour.setDescription(
                    "Изпитайте чудото на древен Египет с пирамидите, Великата сфинкса и Долината на царете;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 1 юни 2025г. в 07:00 ч.;\n" +
                            "Кацане на Летище Луксор (LXR) в 12:00ч.;\n" +
                            "Настаняване: Luxor Royal Hotel, хотел с изглед към река Нил;\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки);\n" +
                            "Активности: Разходки с камили, круизи по река Нил;\n" +
                            "Екскурзии: Посещение на пирамидите в Гиза, Египетски музей, храмове в Луксор;\n" +
                            "Допълнително: Вечери с традиционна египетска кухня, светлинно шоу при пирамидите"
            );
            destinationFour.setImageUrl("egypt.jpg");
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
                    "Потопете се в богатата култура на Япония – от оживен Токио до спокойна Киото;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 15 юли 2025г. в 11:00 ч.;\n" +
                            "Кацане на Летище Нарита (NRT) в 20:00ч.;\n" +
                            "Настаняване: Park Hotel Tokyo, модерен хотел в центъра на Токио;\n" +
                            "Хранене: Полупансион (закуска и вечеря);\n" +
                            "Активности: Посещение на Токио Дисниленд, шопинг в квартал Гинза;\n" +
                            "Екскурзии: Храмове в Киото, планина Фуджи, традиционна чайна церемония;\n" +
                            "Допълнително: Вечери с суши и сашими, представление на гейши"
            );
            destinationFive.setImageUrl("japan.jpg");
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
                    "Открийте чудесата на Австралия – от Големия бариерен риф до Австралийското вътрешно;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 1 август 2025г. в 09:00 ч.;\n" +
                            "Кацане на Летище Сидни (SYD) в 22:00ч.;\n" +
                            "Настаняване: Shangri-La Hotel Sydney, хотел с изглед към залива;\n" +
                            "Хранене: Полупансион (закуска и вечеря);\n" +
                            "Активности: Гмуркане в Големия бариерен риф, сафари в национални паркове;\n" +
                            "Екскурзии: Посещение на Сидни и Мелбърн, Оперен театър в Сидни;\n" +
                            "Допълнително: Вечери с австралийска кухня, обиколки с хеликоптер"
            );
            destinationSix.setImageUrl("australia.jpg");
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
                    "Избягайте в тропическия рай на Тайланд с красиви плажове и богата история;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 5 септември 2025г. в 08:00 ч.;\n" +
                            "Кацане на Летище Пукет (HKT) в 18:00ч.;\n" +
                            "Настаняване: Banyan Tree Phuket, луксозен курорт с частни вили;\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки);\n" +
                            "Активности: Тайландски масажи, водни спортове, плажни игри;\n" +
                            "Екскурзии: Посещение на островите Пхи Пхи, храмове в Банкок;\n" +
                            "Допълнително: Курсове по тайландска кухня, вечерни шоута"
            );
            destinationSeven.setImageUrl("tailand.jpg");
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
                    "Посетете оживените градове Рио де Жанейро и Сао Пауло, изследвайте Амазонската джунгла;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 10 октомври 2025г. в 10:00 ч.;\n" +
                            "Кацане на Летище Рио де Жанейро (GIG) в 20:00 ч.;\n" +
                            "Настаняване: Copacabana Palace, луксозен хотел на плажа Копакабана;\n" +
                            "Хранене: Полупансион (закуска и вечеря);\n" +
                            "Активности: Плажни игри, катерене до статуята Христос Спасител;\n" +
                            "Екскурзии: Посещение на Амазонската джунгла, град Сао Пауло;\n" +
                            "Допълнително: Вечери с бразилска кухня, обиколки с джип"
            );
            destinationEight.setImageUrl("rio.jpg");
            destinationEight.setPrice(2200);
            destinationEight.setStatus("ACCEPTED");
            destinationEight.setLimitedPeople(20);
            destinationEight.setRemainingPeople(20);
            destinationEight.setDateOfDeparture(LocalDate.parse("2025-10-10"));
            destinationEight.setDateOfReturn(LocalDate.parse("2025-10-20"));

            destinationRepository.save(destinationEight);

            destinationNine.setName("Бора Бора – Перлата на Френска Полинезия");
            destinationNine.setDescription(
                    "Бора Бора е известна със своите кристално чисти лагуни и луксозни водни вили. Островът предлага изключителни гледки, богата морска флора и фауна, както и идеални условия за водни спортове;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 25 юни 2025г. в 14:00 ч.;\n" +
                            "Кацане на Летище Бора Бора (BOB) в 06:00 ч. на следващия ден;\n" +
                            "Настаняване: Four Seasons Resort Bora Bora, луксозни вили над водата;\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки);\n" +
                            "Активности: Скуба дайвинг, гмуркане с шнорхел, разходки с лодка, спа процедури;\n" +
                            "Екскурзии: Турове с катамаран по лагуната, посещение на местни села и култура;\n" +
                            "Допълнително: Луксозни вечери с гледка към залеза"
            );
            destinationNine.setImageUrl("boraBora.jpg");
            destinationNine.setPrice(3500);
            destinationNine.setStatus("ACCEPTED");
            destinationNine.setLimitedPeople(20);
            destinationNine.setRemainingPeople(20);
            destinationNine.setDateOfDeparture(LocalDate.parse("2025-06-25"));
            destinationNine.setDateOfReturn(LocalDate.parse("2025-07-05"));

            destinationRepository.save(destinationNine);

            destinationTen.setName("Сейшелските острови – Райски кът в Индийския океан");
            destinationTen.setDescription(
                    "Сейшелите са известни със своите бели плажове, екзотични животни и уникални природни резервати. Идеалното място за релаксация и приключения;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 2 август 2025г. в 12:00 ч.;\n" +
                            "Кацане на Летище Мае (SEZ) в 03:00 ч. на следващия ден;\n" +
                            "Настаняване: Constance Ephelia Seychelles, луксозен курорт на плажа;\n" +
                            "Хранене: Полупансион (закуска и вечеря);\n" +
                            "Активности: Скуба дайвинг, риболов, разходки в природни резервати, изследване на островите;\n" +
                            "Екскурзии: Посещение на националния парк в Сейшелите, лодки с прозрачно дъно;\n" +
                            "Допълнително: Вечери с местни специалитети, романтични обеди на плажа"
            );
            destinationTen.setImageUrl("seychelles.jpg");
            destinationTen.setPrice(4000);
            destinationTen.setStatus("ACCEPTED");
            destinationTen.setLimitedPeople(15);
            destinationTen.setRemainingPeople(15);
            destinationTen.setDateOfDeparture(LocalDate.parse("2025-08-02"));
            destinationTen.setDateOfReturn(LocalDate.parse("2025-08-15"));

            destinationRepository.save(destinationTen);

            destinationEleven.setName("Бали, Индонезия – Островът на боговете");
            destinationEleven.setDescription(
                    "Бали е рай за любителите на културата, природата и релаксацията. Със своите храмове, терасовидни оризища и плажове, островът предлага уникална атмосфера;\n" +
                            "Самолетни билети: Излитане от Летище София (SOF) на 10 септември 2025г. в 18:00 ч.;\n" +
                            "Кацане на Летище Денпасар (DPS) в 05:00 ч. на следващия ден;\n" +
                            "Настаняване: Ayana Resort and Spa, луксозен курорт с панорамни гледки към океана;\n" +
                            "Хранене: All-Inclusive (закуска, обяд, вечеря и напитки);\n" +
                            "Активности: Йога на плажа, разходки в джунглата, разглеждане на храмове, водни спортове;\n" +
                            "Екскурзии: Посещение на храма Танах Лот, културни изживявания в Убуд;\n" +
                            "Допълнително: Традиционни балийски вечери, масаж в спа център"
            );
            destinationEleven.setImageUrl("bali.jpg");
            destinationEleven.setPrice(2800);
            destinationEleven.setStatus("ACCEPTED");
            destinationEleven.setLimitedPeople(30);
            destinationEleven.setRemainingPeople(30);
            destinationEleven.setDateOfDeparture(LocalDate.parse("2025-09-10"));
            destinationEleven.setDateOfReturn(LocalDate.parse("2025-09-20"));

            destinationRepository.save(destinationEleven);

            reservationOne.setDestination(destinationOne);
            reservationOne.setUser(userOne);
            reservationOne.setNumberOfPeople(5);
            reservationOne.setDestinationRated(true);
            reservationOne.setTotalPrice(reservationOne.getNumberOfPeople() * destinationOne.getPrice());
            reservationOne.setStatus("BOOKED");
            destinationOne.setRemainingPeople(destinationOne.getRemainingPeople() - reservationOne.getNumberOfPeople());
            destinationOne.setPopularity(destinationOne.getPopularity() + reservationOne.getNumberOfPeople());

            reservationRepository.save(reservationOne);
            destinationRepository.save(destinationOne);

            reservationTwo.setDestination(destinationTwo);
            reservationTwo.setUser(userOne);
            reservationTwo.setNumberOfPeople(7);
            reservationTwo.setDestinationRated(false);
            reservationTwo.setTotalPrice(reservationTwo.getNumberOfPeople() * destinationTwo.getPrice());
            reservationTwo.setStatus("BOOKED");
            destinationTwo.setRemainingPeople(destinationTwo.getRemainingPeople() - reservationTwo.getNumberOfPeople());
            destinationTwo.setPopularity(destinationTwo.getPopularity() + reservationTwo.getNumberOfPeople());

            reservationRepository.save(reservationTwo);
            destinationRepository.save(destinationTwo);

            reservationThree.setDestination(destinationThree);
            reservationThree.setUser(userOne);
            reservationThree.setNumberOfPeople(3);
            reservationThree.setDestinationRated(false);
            reservationThree.setTotalPrice(reservationThree.getNumberOfPeople() * destinationThree.getPrice());
            reservationThree.setStatus("BOOKED");
            destinationThree.setRemainingPeople(destinationThree.getRemainingPeople() - reservationThree.getNumberOfPeople());
            destinationThree.setPopularity(destinationThree.getPopularity() + reservationThree.getNumberOfPeople());

            reservationRepository.save(reservationThree);
            destinationRepository.save(destinationThree);

            reservationFour.setDestination(destinationOne);
            reservationFour.setUser(userOne);
            reservationFour.setNumberOfPeople(2);
            reservationFour.setDestinationRated(false);
            reservationFour.setTotalPrice(reservationFour.getNumberOfPeople() * destinationOne.getPrice());
            reservationFour.setStatus("BOOKED");
            destinationOne.setRemainingPeople(destinationOne.getRemainingPeople() - reservationFour.getNumberOfPeople());
            destinationOne.setPopularity(destinationOne.getPopularity() + reservationFour.getNumberOfPeople());

            reservationRepository.save(reservationFour);
            destinationRepository.save(destinationOne);

            reservationFive.setDestination(destinationTwo);
            reservationFive.setUser(userOne);
            reservationFive.setNumberOfPeople(7);
            reservationFive.setDestinationRated(false);
            reservationFive.setTotalPrice(reservationFive.getNumberOfPeople() * destinationTwo.getPrice());
            reservationFive.setStatus("BOOKED");
            destinationTwo.setRemainingPeople(destinationTwo.getRemainingPeople() - reservationFive.getNumberOfPeople());
            destinationTwo.setPopularity(destinationTwo.getPopularity() + reservationFive.getNumberOfPeople());

            reservationRepository.save(reservationFive);
            destinationRepository.save(destinationTwo);

            reservationSix.setDestination(destinationThree);
            reservationSix.setUser(userOne);
            reservationSix.setNumberOfPeople(5);
            reservationSix.setDestinationRated(false);
            reservationSix.setTotalPrice(reservationSix.getNumberOfPeople() * destinationThree.getPrice());
            reservationSix.setStatus("BOOKED");
            destinationThree.setRemainingPeople(destinationThree.getRemainingPeople() - reservationSix.getNumberOfPeople());
            destinationThree.setPopularity(destinationThree.getPopularity() + reservationSix.getNumberOfPeople());

            reservationRepository.save(reservationSix);
            destinationRepository.save(destinationThree);

            promotionService.createPromotion(destinationSeven.getId(), 20.00);
            promotionService.createPromotion(destinationSix.getId(), 10.00);
            promotionService.createPromotion(destinationEight.getId(), 15.00);

            campaignService.createCampaign(destinationNine.getId(), 5.00);
            campaignService.createCampaign(destinationTen.getId(), 25.00);
            campaignService.createCampaign(destinationEleven.getId(), 10.00);

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