package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import jakarta.transaction.Transactional;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.Specifications.DestinationSpecifications;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // Constructor for dependency injection
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    // Save a new destination to the database
    public void saveDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    // Fetch a destination by its ID
    public Destination getDestination(long id) {
        return destinationRepository.findById(id).orElseThrow();
    }

    // Update an existing destination
    public void updateDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    // Delete a destination and all related reservations
    @Transactional
    public void deleteDestination(Long id) {
        reservationRepository.deleteByDestinationId(id); // Изтриване на всички резервации, свързани с дестинацията
        destinationRepository.deleteById(id);
    }

    // Get a list of all destinations
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    // Filter and sort destinations based on multiple criteria
    public List<Destination> getFilteredAndSortedDestinations(String keyword, Double minPrice, Double maxPrice,
                                                              Double minRating, String sortBy, LocalDate dateOfReturn, LocalDate dateOfDeparture) {
        Specification<Destination> spec = DestinationSpecifications.filterDestinations(keyword, minPrice, maxPrice, minRating, dateOfReturn, dateOfDeparture);
        Sort sort = Sort.by(Sort.Direction.DESC, "id"); // Default sort by ID in descending order

        // Set the sorting criteria based on the provided input
        switch (sortBy) {
            case "popularity":
                sort = Sort.by(Sort.Direction.DESC, "popularity");
                break;
            case "rating":
                sort = Sort.by(Sort.Direction.DESC, "averageRating");
                break;
            case "price":
                sort = Sort.by(Sort.Direction.ASC, "price");
                break;
        }

        // If no dates are provided, filter only by keyword, price, and rating
        if (dateOfReturn == null && dateOfDeparture == null) {
            spec = DestinationSpecifications.filterDestinations(keyword, minPrice, maxPrice, minRating, null, null);
        }

        return destinationRepository.findAll(spec, sort);
    }

    // Merge-sort algorithm to sort destinations based on a given criterion
    private List<Destination> mergeSort(List<Destination> list, String sortBy) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Destination> left = new ArrayList<>(list.subList(0, mid));
        List<Destination> right = new ArrayList<>(list.subList(mid, list.size()));

        left = mergeSort(left, sortBy);
        right = mergeSort(right, sortBy);

        return merge(left, right, sortBy);
    }

    // Merges two sorted lists based on the specified sort criteria
    private List<Destination> merge(List<Destination> left, List<Destination> right, String sortBy) {
        List<Destination> mergedList = new ArrayList<>();
        int i = 0, j = 0;

        // Merge the lists while sorting by the selected criterion
        while (i < left.size() && j < right.size()) {
            boolean condition = false;

            switch (sortBy) {
                case "price":
                    condition = left.get(i).getPrice() < right.get(j).getPrice();
                    break;
                case "popularity":
                    condition = left.get(i).getPopularity() > right.get(j).getPopularity();
                    break;
                case "rating":
                    condition = left.get(i).getAverageRating() > right.get(j).getAverageRating();
                    break;
            }

            // Add the smaller element to the merged list
            if (condition) {
                mergedList.add(left.get(i));
                i++;
            } else {
                mergedList.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            mergedList.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            mergedList.add(right.get(j));
            j++;
        }

        return mergedList;
    }
}
