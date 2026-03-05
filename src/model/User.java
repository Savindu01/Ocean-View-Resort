package model;

/**
 * User entity class for authentication with role-based access.
 */
public class User {
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

    private int id;
    private String username;
    private String password;
    private Role role;

    public User() {
        this.role = Role.RECEPTIONIST;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = Role.RECEPTIONIST;
    }

    public User(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.RECEPTIONIST;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
