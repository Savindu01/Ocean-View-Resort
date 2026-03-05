import controller.AuthController;
import dao.DatabaseConnection;
import view.LoginFrame;

import javax.swing.*;

/**
 * Main entry point for the Ocean View Resort Reservation System.
 */
public class Main {

    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Initialize database connection on startup
        SwingUtilities.invokeLater(() -> {
            try {
                // Test database connection
                DatabaseConnection.getInstance();
                System.out.println("Application starting...");

                // Initialize auth controller and default user
                AuthController authController = new AuthController();
                authController.initializeDefaultUser();

                // Show login frame
                LoginFrame loginFrame = new LoginFrame(authController);
                loginFrame.setVisible(true);

            } catch (Exception e) {
                System.err.println("Failed to start application: " + e.getMessage());
                JOptionPane.showMessageDialog(
                    null,
                    "Failed to connect to database.\n\n" +
                    "Please ensure:\n" +
                    "1. MySQL server is running\n" +
                    "2. Database 'ocean_view_resort' exists\n" +
                    "3. Database credentials are correct\n\n" +
                    "Error: " + e.getMessage(),
                    "Database Connection Error",
                    JOptionPane.ERROR_MESSAGE
                );
                System.exit(1);
            }
        });
    }
}
