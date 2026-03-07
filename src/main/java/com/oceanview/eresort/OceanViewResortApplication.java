package com.oceanview.eresort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for Ocean View Resort.
 * 
 * @SpringBootApplication is a convenience annotation that combines:
 * - @Configuration: Tags the class as a source of bean definitions
 * - @EnableAutoConfiguration: Tells Spring Boot to auto-configure based on dependencies
 * - @ComponentScan: Tells Spring to scan for components in this package and sub-packages
 */
@SpringBootApplication
public class OceanViewResortApplication {

    public static void main(String[] args) {
        SpringApplication.run(OceanViewResortApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("🏨 Ocean View Resort API is running!");
        System.out.println("📍 URL: http://localhost:8080");
        System.out.println("📚 API Docs: http://localhost:8080/api");
        System.out.println("===========================================\n");
    }
}
