package model;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsModel {
    private Connection conn;

    public SettingsModel() {
        conn = DatabaseConnection.getConnection();
    }

    public ResultSet getUserDetails(String userId) throws SQLException {
        String query = "SELECT FullName, email, DateOfBirth, gender FROM users WHERE user_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, userId);
        return statement.executeQuery();
    }

    public int updateUserDetails(String fullName, String email, String dateOfBirth, String gender, String userId) throws SQLException {
        String query = "UPDATE users SET FullName = ?, email = ?, DateOfBirth = ?, gender = ? WHERE user_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, fullName);
        statement.setString(2, email);
        statement.setString(3, dateOfBirth);
        statement.setString(4, gender);
        statement.setString(5, userId);
        return statement.executeUpdate();
    }

    public ResultSet getUserPassword(String userId) throws SQLException {
        String query = "SELECT password FROM users WHERE user_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, userId);
        return statement.executeQuery();
    }

    public int updateUserPassword(String newPassword, String userId) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, newPassword);
        statement.setString(2, userId);
        return statement.executeUpdate();
    }
}
