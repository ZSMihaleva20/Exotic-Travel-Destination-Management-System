package org.codingburgas.zsmihaleva20.exotic_destination_management_system.Specifications;

import jakarta.persistence.criteria.Predicate;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DestinationSpecifications {

    public static Specification<Destination> filterDestinations(String keyword, Double minPrice, Double maxPrice,
                                                                Double minRating, LocalDate dateOfArrival, LocalDate dateOfDeparture) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("status"), "ACCEPTED"));

            if (keyword != null && !keyword.trim().isEmpty()) {
                String keywordPattern = "%" + keyword.toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), keywordPattern);
                Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keywordPattern);
                predicates.add(criteriaBuilder.or(namePredicate, descriptionPredicate));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (minRating != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"), minRating));
            }

            if (dateOfArrival != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfDeparture"), dateOfArrival));
            }

            if (dateOfDeparture != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfArrival"), dateOfDeparture));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
