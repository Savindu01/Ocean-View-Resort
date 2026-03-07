package com.oceanview.eresort.service;

import com.oceanview.eresort.model.User;
import com.oceanview.eresort.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Service layer for authentication and user management.
 * Contains business logic for user operations.
 */
@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticate user with username and password.
     * 
     * @param username the username
     * @param password the plain text password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }

        return userRepository.findByUsername(username)
                .filter(user -> verifyPassword(password, user.getPassword()))
                .orElse(null);
    }

    /**
     * Create a new user with hashed password.
     * 
     * @param username the username
     * @param password the plain text password
     * @param role the user role
     * @return created User object
     */
    public User createUser(String username, String password, User.Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashPassword(password));
        user.setRole(role != null ? role : User.Role.RECEPTIONIST);

        return userRepository.save(user);
    }

    /**
     * Initialize default admin user if not exists.
     */
    public void initializeDefaultUser() {
        if (!userRepository.existsByUsername("admin")) {
            createUser("admin", "admin123", User.Role.ADMIN);
            System.out.println("✅ Default admin user created (username: admin, password: admin123)");
        }
    }

    /**
     * Check if username exists.
     */
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Hash password using SHA-256.
     * 
     * @param password plain text password
     * @return hashed password as hex string
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Verify password against hashed password.
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return hashPassword(plainPassword).equals(hashedPassword);
    }
}
