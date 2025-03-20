package org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//Rating Repository
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.destination.id = ?1")
    Double getAverageRatingByDestination(Long destinationId);
}
