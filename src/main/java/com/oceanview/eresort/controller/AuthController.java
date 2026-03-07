package com.oceanview.eresort.controller;

import com.oceanview.eresort.dto.LoginRequest;
import com.oceanview.eresort.dto.LoginResponse;
import com.oceanview.eresort.model.User;
import com.oceanview.eresort.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication endpoints.
 * Handles login and user management operations.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login endpoint.
     * POST /api/login
     * 
     * @param loginRequest contains username and password
     * @return LoginResponse with success status
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        
        if (user != null) {
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", user.getUsername(), user.getRole().name()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, "Invalid credentials", null, null));
        }
    }

    /**
     * Check if username exists.
     * GET /api/users/exists?username=admin
     */
    @GetMapping("/users/exists")
    public ResponseEntity<Boolean> userExists(@RequestParam String username) {
        return ResponseEntity.ok(authService.userExists(username));
    }
}
