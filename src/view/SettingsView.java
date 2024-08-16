package view;

import controller.SettingsController;
import com.toedter.calendar.JDateChooser;
import view.util.FontLoad;
import view.util.UserSession;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    public static JPanel detailsPanel;
    public static JPanel passwordPanel;
    private SettingsController userController;

    public SettingsView() {
        userController = new SettingsController();

        // Set panel properties
        setBackground(Color.WHITE);
        setBounds(20, 20, 1000, 1000);
        setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setBounds(100, 20, 200, 50);
        titleLabel.setFont(FontLoad.getMontserratBold(30f));

        // Settings icon
        ImageIcon imageIcon = new ImageIcon("img//settings_big.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(15, 10, 64, 64);

        // Add icon and title to panel
        add(imageLabel);
        add(titleLabel);

        // User Details Panel
        detailsPanel = new JPanel();
        detailsPanel.setBounds(20, 100, 460, 300);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "User Details", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FontLoad.getMontserratBold(20f)));
        detailsPanel.setFont(FontLoad.getMontserratBold(20f));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setLayout(null);

        // Labels and text fields
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(15, 45, 100, 25);
        nameLabel.setFont(FontLoad.getMontserratMedium(14f));
        JTextField nameField = new JTextField();
        nameField.setBounds(120, 40, 320, 40);
        nameField.setFont(FontLoad.getMontserratRegular(15f));
        detailsPanel.add(nameLabel);
        detailsPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(15, 95, 100, 25);
        emailLabel.setFont(FontLoad.getMontserratMedium(14f));
        JTextField emailField = new JTextField();
        emailField.setBounds(120, 90, 320, 40);
        emailField.setFont(FontLoad.getMontserratRegular(15f));
        detailsPanel.add(emailLabel);
        detailsPanel.add(emailField);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(15, 145, 100, 25);
        dobLabel.setFont(FontLoad.getMontserratMedium(14f));
        JDateChooser dobChooser = new JDateChooser();
        dobChooser.setDateFormatString("yyyy-MM-dd");
        dobChooser.setBounds(120, 140, 320, 40);
        dobChooser.setFont(FontLoad.getMontserratRegular(15f));
        ImageIcon customIcon = new ImageIcon("img/due.png");
        dobChooser.setIcon(customIcon);
        JTextField dateEditor = ((JTextField) dobChooser.getDateEditor().getUiComponent());
        dateEditor.setEditable(false);
        dateEditor.setBackground(Color.WHITE);
        detailsPanel.add(dobLabel);
        detailsPanel.add(dobChooser);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(15, 195, 100, 25);
        genderLabel.setFont(FontLoad.getMontserratMedium(14f));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderComboBox.setBounds(120, 190, 320, 40);
        genderComboBox.setFont(FontLoad.getMontserratRegular(15f));
        genderComboBox.setBackground(Color.WHITE);
        detailsPanel.add(genderLabel);
        detailsPanel.add(genderComboBox);

        // Save Details Button
        JButton saveButton = new JButton("Confirm");
        saveButton.setBounds(290, 240, 150, 40);
        saveButton.setFont(FontLoad.getMontserratBold(16f));
        saveButton.setBackground(Color.decode("#4682B4"));
        saveButton.setForeground(Color.WHITE);
        detailsPanel.add(saveButton);

        // Add User Details panel to main panel
        add(detailsPanel);

        // Password Change Panel
        passwordPanel = new JPanel();
        passwordPanel.setBounds(20, 430, 460, 270);
        passwordPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Change Password", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FontLoad.getMontserratBold(20f)));
        passwordPanel.setFont(FontLoad.getMontserratBold(20f));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setLayout(null);

        JLabel currentPasswordLabel = new JLabel("Current:");
        currentPasswordLabel.setBounds(15, 45, 130, 25);
        currentPasswordLabel.setFont(FontLoad.getMontserratMedium(14f));
        JPasswordField currentPasswordField = new JPasswordField();
        currentPasswordField.setFont(FontLoad.getMontserratRegular(15f));
        currentPasswordField.setBounds(120, 40, 320, 40);
        passwordPanel.add(currentPasswordLabel);
        passwordPanel.add(currentPasswordField);

        JLabel newPasswordLabel = new JLabel("New:");
        newPasswordLabel.setBounds(15, 95, 130, 25);
        newPasswordLabel.setFont(FontLoad.getMontserratMedium(14f));
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(120, 90, 320, 40);
        newPasswordField.setFont(FontLoad.getMontserratRegular(15f));
        passwordPanel.add(newPasswordLabel);
        passwordPanel.add(newPasswordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm:");
        confirmPasswordLabel.setBounds(15, 145, 130, 25);
        confirmPasswordLabel.setFont(FontLoad.getMontserratMedium(14f));
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(120, 140, 320, 40);
        confirmPasswordField.setFont(FontLoad.getMontserratRegular(15f));
        passwordPanel.add(confirmPasswordLabel);
        passwordPanel.add(confirmPasswordField);

        // Show/Hide Password Checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Passwords");
        showPasswordCheckBox.setBounds(115, 180, 150, 25);
        showPasswordCheckBox.setFont(FontLoad.getMontserratMedium(14f));
        showPasswordCheckBox.setBackground(Color.WHITE);
        passwordPanel.add(showPasswordCheckBox);

        // Change Password Button
        JButton changePasswordButton = new JButton("Confirm");
        changePasswordButton.setBounds(290, 210, 150, 40);
        changePasswordButton.setFont(FontLoad.getMontserratBold(16f));
        changePasswordButton.setBackground(Color.decode("#4682B4"));
        changePasswordButton.setForeground(Color.WHITE);
        passwordPanel.add(changePasswordButton);

        // Add Password Change panel to main panel
        add(passwordPanel);

        // Load user details from the database
        userController.loadUserDetails(nameField, emailField, dobChooser, genderComboBox);

        // Save Button Action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dob = dobChooser.getDate() != null ? String.format("%tF", dobChooser.getDate()) : "";
                userController.saveUserDetails(nameField.getText(), emailField.getText(), dob, (String) genderComboBox.getSelectedItem());
            }
        });

        // Change Password Button Action
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                userController.changeUserPassword(currentPassword, newPassword, confirmPassword);
            }
        });

        // Show/Hide Password Action
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean showPassword = showPasswordCheckBox.isSelected();
                currentPasswordField.setEchoChar(showPassword ? (char) 0 : '*');
                newPasswordField.setEchoChar(showPassword ? (char) 0 : '*');
                confirmPasswordField.setEchoChar(showPassword ? (char) 0 : '*');
            }
        });
    }
}
