package com.oceanview.eresort.config;

import com.oceanview.eresort.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Component that runs on application startup to initialize default data.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;

    @Autowired
    public DataInitializer(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n🔧 Initializing application data...");
        authService.initializeDefaultUser();
        System.out.println("✅ Application initialization complete!\n");
    }
}
