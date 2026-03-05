-- Ocean View Resort Database Schema
-- MySQL Database Setup Script

CREATE DATABASE IF NOT EXISTS ocean_view_resort;
USE ocean_view_resort;

-- Users table for authentication with role-based access
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'RECEPTIONIST', 'MANAGER') DEFAULT 'RECEPTIONIST'
);

-- Reservations table
CREATE TABLE IF NOT EXISTS reservations (
    reservation_number VARCHAR(20) PRIMARY KEY,
    guest_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    room_type ENUM('SINGLE', 'DOUBLE', 'FAMILY', 'SUITE') NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Default admin user (admin/admin123) is created automatically
-- by the application on first run via AuthController.initializeDefaultUser()
