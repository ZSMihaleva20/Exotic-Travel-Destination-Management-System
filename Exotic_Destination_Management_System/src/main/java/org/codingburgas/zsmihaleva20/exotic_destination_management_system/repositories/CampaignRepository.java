package org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
