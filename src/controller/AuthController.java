package controller;

import dao.UserDAO;
import model.User;

/**
 * Controller for authentication logic.
 */
public class AuthController {
    private final UserDAO userDAO;
    private User currentUser;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    /**
     * Attempt to login with given credentials.
     * @param username the username
     * @param password the password
     * @return true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }

        User user = userDAO.authenticate(username.trim(), password);
        if (user != null) {
            this.currentUser = user;
            return true;
        }
        return false;
    }

    /**
     * Logout the current user.
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Check if a user is currently logged in.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Get the currently logged in user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Initialize default user in database.
     */
    public void initializeDefaultUser() {
        userDAO.initializeDefaultUser();
    }
}
