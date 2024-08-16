import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ToDosql extends JFrame {

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    public ToDosql() {
        // Set up the frame
        setTitle("Sign Up");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(500, 400));
        ImageIcon icon = new ImageIcon("Images.png"); // Adjust the path as needed
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(500, 400, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.WEST);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10));
        
        // Title Label
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create and add components
        JLabel nameLabel = new JLabel("Full Name:");
        JTextField nameField = new JTextField();
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        
        JLabel dobLabel = new JLabel("Date of Birth:");
        JTextField dobField = new JTextField("YYYY-MM-DD");
        
        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        
        // Password
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton togglePasswordButton = new JButton(new ImageIcon("Images/eye_closed.png"));
        
        togglePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility(passwordField, togglePasswordButton);
            }
        });
        
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(togglePasswordButton, BorderLayout.EAST);

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();
        JButton toggleConfirmPasswordButton = new JButton(new ImageIcon("Images/eye_closed.png"));
        
        toggleConfirmPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility(confirmPasswordField, toggleConfirmPasswordButton);
            }
        });

        JPanel confirmPasswordPanel = new JPanel(new BorderLayout());
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);
        confirmPasswordPanel.add(toggleConfirmPasswordButton, BorderLayout.EAST);

        JButton signupButton = new JButton("Sign Up");
        JLabel messageLabel = new JLabel();
        
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = nameField.getText();
                String email = emailField.getText();
                String dob = dobField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (fullName.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    messageLabel.setText("Please fill in all fields.");
                } else if (!password.equals(confirmPassword)) {
                    messageLabel.setText("Passwords do not match.");
                } else {
                    try {
                        // Call method to insert user data into the database
                        boolean isInserted = insertUser(fullName, email, dob, gender, password);
                        if (isInserted) {
                            messageLabel.setText("Sign up successful!");
                        } else {
                            messageLabel.setText("Sign up failed. Please try again.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        messageLabel.setText("Database error. Please try again later.");
                    }
                }
            }
        });
        
        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(dobLabel);
        formPanel.add(dobField);
        formPanel.add(genderLabel);
        formPanel.add(genderComboBox);
        formPanel.add(passwordLabel);
        formPanel.add(passwordPanel);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordPanel);
        formPanel.add(signupButton);
        formPanel.add(messageLabel);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel alreadyHaveAccountLabel = new JLabel("Already have an account?");
        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Redirecting to login page...");
                // Add your login page redirection logic here
            }
        });
        
        loginPanel.add(alreadyHaveAccountLabel);
        loginPanel.add(loginButton);

        // Add form panel and login button to the frame
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(loginPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    private void togglePasswordVisibility(JPasswordField passwordField, JButton toggleButton) {
        if (passwordField.getEchoChar() == '•') {
            passwordField.setEchoChar((char) 0);
            toggleButton.setIcon(new ImageIcon("Images/eye_open.png"));
        } else {
            passwordField.setEchoChar('•');
            toggleButton.setIcon(new ImageIcon("Images/eye_closed.png"));
        }
    }
    
    private boolean insertUser(String fullName, String email, String dob, String gender, String password) throws SQLException {
        String insertQuery = "INSERT INTO users (full_name, email, dob, gender, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, dob);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, password);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDosql signupPage = new ToDosql();
            signupPage.setVisible(true);
        });
    }
}
