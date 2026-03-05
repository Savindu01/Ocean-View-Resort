import dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbInspect {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String sql = "SELECT id, username, password FROM users";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                System.out.println("users table contents:");
                int count = 0;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    System.out.println("id=" + id + ", username=" + username + ", passwordHash=" + password);
                    count++;
                }
                if (count == 0) System.out.println("(no users found)");
            }
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Runtime error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
