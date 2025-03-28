package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository; // Repository for interacting with the User data
    private PasswordEncoder passwordEncoder; // Encoder for securely storing passwords

    // Constructor with both UserRepository and PasswordEncoder injected
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Constructor with only UserRepository injected (in case PasswordEncoder is not needed)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Create and save a new user with an encrypted password.
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // Get a user by their ID.
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    // Get a user by their username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Update an existing user's details
    public void updateUser(User user) {
        userRepository.save(user);
    }

    // Delete a user by their ID
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // Load a user by their username (required for Spring Security)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.findByUsername(username) == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}