package com.oceanview.eresort.repository;

import com.oceanview.eresort.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for User entity.
 * Provides CRUD operations and custom query methods.
 * 
 * Spring Data JPA automatically implements these methods at runtime!
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by username.
     * Spring Data JPA generates the query: SELECT * FROM users WHERE username = ?
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if username exists.
     * Spring Data JPA generates the query: SELECT COUNT(*) > 0 FROM users WHERE username = ?
     */
    boolean existsByUsername(String username);

    /**
     * Find user by username and role.
     */
    Optional<User> findByUsernameAndRole(String username, User.Role role);
}
