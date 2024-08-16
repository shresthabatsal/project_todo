package controller;

import model.SettingsModel;
import view.SettingsView;
import view.util.UserSession;
import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsController {
    private SettingsModel userModel;

    public SettingsController() {
        userModel = new SettingsModel();
    }

    public void loadUserDetails(JTextField nameField, JTextField emailField, JDateChooser dobChooser, JComboBox<String> genderComboBox) {
        String userId = UserSession.getUserId();
        try {
            ResultSet rs = userModel.getUserDetails(userId);
            if (rs.next()) {
                nameField.setText(rs.getString("FullName"));
                emailField.setText(rs.getString("email"));
                dobChooser.setDate(rs.getDate("DateOfBirth"));
                String genderFromDB = rs.getString("gender").trim().toLowerCase();
                for (int i = 0; i < genderComboBox.getItemCount(); i++) {
                    String genderItem = genderComboBox.getItemAt(i).trim().toLowerCase();
                    if (genderItem.equals(genderFromDB)) {
                        genderComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(SettingsView.detailsPanel, "Error loading user details.");
        }
    }

    public void saveUserDetails(String fullName, String email, String dateOfBirth, String gender) {
        String userId = UserSession.getUserId();
        try {
            int rowsUpdated = userModel.updateUserDetails(fullName, email, dateOfBirth, gender, userId);
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(SettingsView.detailsPanel, "User details updated successfully.");
            } else {
                JOptionPane.showMessageDialog(SettingsView.detailsPanel, "Failed to update user details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(SettingsView.detailsPanel, "Error updating user details.");
        }
    }

    public void changeUserPassword(String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(SettingsView.passwordPanel, "New password and confirm password do not match.");
            return;
        }

        String userId = UserSession.getUserId();
        try {
            ResultSet rs = userModel.getUserPassword(userId);
            if (rs.next()) {
                String currentPasswordFromDB = rs.getString("password");
                if (!currentPassword.equals(currentPasswordFromDB)) {
                    JOptionPane.showMessageDialog(SettingsView.passwordPanel, "Current password is incorrect.");
                    return;
                }

                int rowsUpdated = userModel.updateUserPassword(newPassword, userId);
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(SettingsView.passwordPanel, "Password changed successfully.");
                } else {
                    JOptionPane.showMessageDialog(SettingsView.passwordPanel, "Failed to change password.");
                }
            } else {
                JOptionPane.showMessageDialog(SettingsView.passwordPanel, "User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(SettingsView.passwordPanel, "Error changing password.");
        }
    }
}
