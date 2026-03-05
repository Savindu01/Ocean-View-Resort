package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for MySQL database connection management.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

        // Database configuration - values can be provided via environment variables.
        // Environment variable names: DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASS
        private static final String HOST = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        private static final String PORT = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
        private static final String DATABASE = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "ocean_view_resort";
        private static final String USERNAME = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
        private static final String PASSWORD = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "4087";

        private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE +
            "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private DatabaseConnection() {
        try {
            // Debug: print DB config summary (do not print actual password in logs)
            System.out.println("[DB DEBUG] URL=" + URL);
            System.out.println("[DB DEBUG] USERNAME='" + USERNAME + "'");
            System.out.println("[DB DEBUG] PASSWORD is null? " + (PASSWORD == null));
            System.out.println("[DB DEBUG] PASSWORD length=" + (PASSWORD == null ? "null" : PASSWORD.length()));
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null || !isConnectionValid()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (SQLException e) {
            instance = new DatabaseConnection();
        }
        return connection;
    }

    private static boolean isConnectionValid() {
        try {
            return instance != null && instance.connection != null &&
                   !instance.connection.isClosed() && instance.connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
