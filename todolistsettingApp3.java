import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class todolistsettingApp3 {

    // Simulate a database with an in-memory user
    private static User currentUser = new User("Jane Doe", "jane.doe@example.com", "987-654-3210", "456 Elm St", "securepassword");

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("To-Do List - User Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);
        frame.setLayout(null);

        // User Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBounds(20, 20, 320, 220);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "User Details"));
        detailsPanel.setLayout(null);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        JTextField nameField = new JTextField(currentUser.getName());
        nameField.setBounds(100, 20, 200, 25);
        detailsPanel.add(nameLabel);
        detailsPanel.add(nameField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 50, 80, 25);
        JTextField emailField = new JTextField(currentUser.getEmail());
        emailField.setBounds(100, 50, 200, 25);
        detailsPanel.add(emailLabel);
        detailsPanel.add(emailField);

        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 80, 80, 25);
        JTextField phoneField = new JTextField(currentUser.getPhone());
        phoneField.setBounds(100, 80, 200, 25);
        detailsPanel.add(phoneLabel);
        detailsPanel.add(phoneField);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 110, 80, 25);
        JTextField addressField = new JTextField(currentUser.getAddress());
        addressField.setBounds(100, 110, 200, 25);
        detailsPanel.add(addressLabel);
        detailsPanel.add(addressField);

        // Save Button
        JButton saveButton = new JButton("Save Details");
        saveButton.setBounds(100, 140, 150, 25);
        saveButton.setForeground(Color.BLUE); // Change button text color to blue
        detailsPanel.add(saveButton);

        // Password Change Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setBounds(360, 20, 320, 220);
        passwordPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Change Password"));
        passwordPanel.setLayout(null);

        // Current Password
        JLabel currentPasswordLabel = new JLabel("Current Password:");
        currentPasswordLabel.setBounds(10, 20, 130, 25);
        JPasswordField currentPasswordField = new JPasswordField();
        currentPasswordField.setBounds(150, 20, 150, 25);
        passwordPanel.add(currentPasswordLabel);
        passwordPanel.add(currentPasswordField);

        // New Password
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(10, 50, 130, 25);
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(150, 50, 150, 25);
        passwordPanel.add(newPasswordLabel);
        passwordPanel.add(newPasswordField);

        // Confirm New Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(10, 80, 130, 25);
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(150, 80, 150, 25);
        passwordPanel.add(confirmPasswordLabel);
        passwordPanel.add(confirmPasswordField);

        // Show/Hide Password Checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(150, 110, 150, 25);
        passwordPanel.add(showPasswordCheckBox);

        // Change Password Button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(100, 140, 150, 25);
        changePasswordButton.setForeground(Color.BLUE); // Change button text color to blue
        passwordPanel.add(changePasswordButton);

        // Add panels to frame
        frame.add(detailsPanel);
        frame.add(passwordPanel);
        frame.setVisible(true);

        // Save Button Action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser.setName(nameField.getText());
                currentUser.setEmail(emailField.getText());
                currentUser.setPhone(phoneField.getText());
                currentUser.setAddress(addressField.getText());
                JOptionPane.showMessageDialog(frame, "Details updated successfully.");
            }
        });

        // Change Password Button Action
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!currentUser.getPassword().equals(currentPassword)) {
                    JOptionPane.showMessageDialog(frame, "Current password is incorrect.");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "New passwords do not match.");
                    return;
                }

                currentUser.setPassword(newPassword);
                JOptionPane.showMessageDialog(frame, "Password changed successfully.");
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

class User {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;

    public User(String name, String email, String phone, String address, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
