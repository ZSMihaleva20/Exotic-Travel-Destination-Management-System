package org.codingburgas.zsmihaleva20.exotic_destination_management_system;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.repositories.UserRepository;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setUserID(1);
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
    }

    @Test
    void createUser_ShouldEncodePasswordAndSaveUser() {
        // Arrange
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn(encodedPassword);

        // Act
        userService.createUser(testUser);

        // Assert
        assertEquals(encodedPassword, testUser.getPassword());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser));

        // Act
        User returnedUser = userService.getUserById(1);

        // Assert
        assertNotNull(returnedUser);
        assertEquals(testUser.getUserID(), returnedUser.getUserID());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserById_ShouldThrowExceptionIfUserNotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> userService.getUserById(1));
    }

    @Test
    void getUserByUsername_ShouldReturnUser() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);

        // Act
        User returnedUser = userService.getUserByUsername("testUser");

        // Assert
        assertNotNull(returnedUser);
        assertEquals(testUser.getUsername(), returnedUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void getUserByUsername_ShouldThrowUsernameNotFoundExceptionIfUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("testUser"));
    }

    @Test
    void updateUser_ShouldSaveUser() {
        // Arrange
        testUser.setUsername("updatedUser");
        when(userRepository.save(testUser)).thenReturn(testUser);

        // Act
        userService.updateUser(testUser);

        // Assert
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1);

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void loadUserByUsername_ShouldThrowExceptionIfUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonExistentUser"));
    }
}
