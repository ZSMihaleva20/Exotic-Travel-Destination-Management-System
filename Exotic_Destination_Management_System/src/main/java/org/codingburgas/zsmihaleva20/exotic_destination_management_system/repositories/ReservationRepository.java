package org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatus(String status);
}
