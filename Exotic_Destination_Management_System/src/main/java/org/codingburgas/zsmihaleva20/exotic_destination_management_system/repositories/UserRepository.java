package org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//User Repository
@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String userEmail);
}