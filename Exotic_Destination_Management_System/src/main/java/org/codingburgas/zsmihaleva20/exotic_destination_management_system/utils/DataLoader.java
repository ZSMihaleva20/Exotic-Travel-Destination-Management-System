package org.codingburgas.zsmihaleva20.exotic_destination_management_system.utils;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        createAdmin();
    }

    private void createAdmin() {
        userRepository.save(new User("admin", passwordEncoder.encode("admin"), "admin@email.com", "ADMIN"));
        userRepository.save(new User("manager", passwordEncoder.encode("manager"), "manager@email.com", "MANAGER"));
    }
}