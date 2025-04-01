package org.codingburgas.zsmihaleva20.exotic_destination_management_system.specifications;

import jakarta.persistence.criteria.Predicate;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DestinationSpecifications {

    // Creates a Specification for filtering destinations based on various criteria.
    public static Specification<Destination> filterDestinations(String keyword, Double minPrice, Double maxPrice,
                                                                Double minRating, LocalDate dateOfReturn, LocalDate dateOfDeparture) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("status"), "ACCEPTED"));

            // If a keyword is provided, filter by name or description containing the keyword
            if (keyword != null && !keyword.trim().isEmpty()) {
                String keywordPattern = "%" + keyword.toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), keywordPattern);
                Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keywordPattern);
                predicates.add(criteriaBuilder.or(namePredicate, descriptionPredicate));
            }

            // If minPrice is provided, filter destinations with a price greater than or equal to the minPrice
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            // If maxPrice is provided, filter destinations with a price less than or equal to the maxPrice
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            // If minRating is provided, filter destinations with a rating greater than or equal to the minRating
            if (minRating != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"), minRating));
            }

            // If dateOfReturn is provided, filter destinations where the departure date is greater than or equal to the dateOfReturn
            if (dateOfReturn != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfDeparture"), dateOfReturn));
            }


            // If dateOfDeparture is provided, filter destinations where the return date is less than or equal to the dateOfDeparture
            if (dateOfDeparture != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfReturn"), dateOfDeparture));
            }

            // Combine all predicates with an AND condition
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
