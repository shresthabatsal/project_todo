package model;

import database.DatabaseConnection;
import view.util.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    public boolean authenticate(String email, String password) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT user_id FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String userId = rs.getString("user_id");
                        UserSession.setUserId(userId);
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Don't close the connection here; manage it elsewhere in the application
        }
        return false;
    }
}
