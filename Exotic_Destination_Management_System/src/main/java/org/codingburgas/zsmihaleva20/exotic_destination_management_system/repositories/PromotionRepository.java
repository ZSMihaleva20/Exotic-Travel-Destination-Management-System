package org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Promotion Repository
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
