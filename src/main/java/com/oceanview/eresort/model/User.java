package com.oceanview.eresort.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * User entity for authentication with role-based access.
 * JPA entity mapped to 'users' table.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * User role enum for role-based access control.
     */
    public enum Role {
        ADMIN("Administrator"),
        RECEPTIONIST("Receptionist"),
        MANAGER("Manager");

        private final String displayName;

        Role(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static Role fromString(String role) {
            try {
                return Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                return RECEPTIONIST; // default role
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Password is required")
    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.RECEPTIONIST;

    // Constructors
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.RECEPTIONIST;
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Helper methods
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isManager() {
        return role == Role.MANAGER || role == Role.ADMIN;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', role='" + role.getDisplayName() + "'}";
    }
}
