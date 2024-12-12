package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public void saveDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    public Destination getDestination(long id) {
        return destinationRepository.findById(id).orElseThrow();
    }

    public void updateDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }
}
