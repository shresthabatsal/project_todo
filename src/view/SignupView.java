package view;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import controller.SignupController;
import database.DatabaseConnection;
import view.util.FontLoad;

public class SignupView extends JFrame {

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private SignupController controller;

    public SignupView() {
        setBounds(0, 0, 1920, 1080);
        setTitle("Sign Up");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Connection connection = DatabaseConnection.getConnection();
        controller = new SignupController(connection);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(970, 20, 350, 700);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1)));

        ImagePanel imagePanel = new ImagePanel("img/to_do.png", 900, 700);

        imagePanel.setBounds(40, 20, 900, 700);
        add(imagePanel);
        add(formPanel);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(FontLoad.getMontserratBold(23f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 30, 100, 50);
        formPanel.add(titleLabel);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(25, 105, 100, 25);
        nameLabel.setFont(FontLoad.getMontserratMedium(14f));
        JTextField nameField = new JTextField();
        nameField.setBounds(25, 130, 300, 40);
        nameField.setFont(FontLoad.getMontserratRegular(15f));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(25, 180, 150, 30);
        emailLabel.setFont(FontLoad.getMontserratMedium(14f));
        JTextField emailField = new JTextField();
        emailField.setBounds(25, 205, 300, 40);
        emailField.setFont(FontLoad.getMontserratRegular(15f));

        JLabel dobLabel = new JLabel("Date of Birth");
        dobLabel.setBounds(25, 255, 150, 30);
        dobLabel.setFont(FontLoad.getMontserratMedium(14f));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(25, 280, 300, 40);
        dateChooser.setFont(FontLoad.getMontserratRegular(15f));
        ImageIcon customIcon = new ImageIcon("img/due.png");
        dateChooser.setIcon(customIcon);
        JTextField dateEditor = ((JTextField) dateChooser.getDateEditor().getUiComponent());
        dateEditor.setEditable(false);
        dateEditor.setBackground(Color.WHITE);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setBounds(25, 330, 150, 30);
        genderLabel.setFont(FontLoad.getMontserratMedium(14f));
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        genderComboBox.setBounds(25, 355, 300, 40);
        genderComboBox.setFont(FontLoad.getMontserratRegular(15f));
        genderComboBox.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(25, 405, 150, 30);
        passwordLabel.setFont(FontLoad.getMontserratMedium(14f));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(25, 430, 270, 40);
        passwordField.setFont(FontLoad.getMontserratRegular(15f));
        JButton togglePasswordButton = new JButton(new ImageIcon("img/show_password.png"));
        togglePasswordButton.setBackground(Color.WHITE);
        togglePasswordButton.setBounds(295, 430, 30, 40);
        togglePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility(passwordField, togglePasswordButton);
            }
        });

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(25, 480, 150, 30);
        confirmPasswordLabel.setFont(FontLoad.getMontserratMedium(14f));
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(25, 505, 270, 40);
        confirmPasswordField.setFont(FontLoad.getMontserratRegular(15f));
        JButton toggleConfirmPasswordButton = new JButton(new ImageIcon("img/show_password.png"));
        toggleConfirmPasswordButton.setBounds(295, 505, 30, 40);
        toggleConfirmPasswordButton.setBackground(Color.WHITE);
        toggleConfirmPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility(confirmPasswordField, toggleConfirmPasswordButton);
            }
        });

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(25, 570, 300, 40);
        signupButton.setBackground(Color.decode("#4682B4"));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(FontLoad.getMontserratBold(15f));

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = nameField.getText();
                String email = emailField.getText();
                Date dob = dateChooser.getDate(); // Getting the date from JDateChooser
                String gender = (String) genderComboBox.getSelectedItem();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (fullName.isEmpty() || email.isEmpty() || dob == null || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(formPanel, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        // Format the Date as String
                        String dobStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(dob);
                        boolean isInserted = controller.handleSignup(fullName, email, dobStr, gender, password, confirmPassword);
                        if (isInserted) {
                            JOptionPane.showMessageDialog(formPanel, "Sign up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            nameField.setText("");
                            emailField.setText("");
                            dateChooser.setDate(null);
                            genderComboBox.setSelectedIndex(0);
                            passwordField.setText("");
                            confirmPasswordField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(formPanel, "Sign up failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(formPanel, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        formPanel.add(signupButton);

        JLabel alreadyHaveAccountLabel = new JLabel("Already have an account?");
        alreadyHaveAccountLabel.setBounds(35, 620, 300, 30);
        alreadyHaveAccountLabel.setFont(FontLoad.getMontserratMedium(16f));
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(250, 624, 60, 20);
        loginButton.setBackground(Color.WHITE);
        loginButton.setFont(FontLoad.getMontserratBold(16f));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView loginPage = new LoginView();
                loginPage.setVisible(true);
                dispose();
            }
        });

        formPanel.add(alreadyHaveAccountLabel);
        formPanel.add(loginButton);
        formPanel.add(titleLabel);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(dobLabel);
        formPanel.add(dateChooser); // Adding JDateChooser to the form
        formPanel.add(genderLabel);
        formPanel.add(genderComboBox);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(togglePasswordButton);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        formPanel.add(toggleConfirmPasswordButton);

        add(formPanel);
    }

    private void togglePasswordVisibility(JPasswordField passwordField, JButton toggleButton) {
        if (passwordField.getEchoChar() == '•') {
            passwordField.setEchoChar((char) 0);
            toggleButton.setIcon(new ImageIcon("img/hide_password.png"));
        } else {
            passwordField.setEchoChar('•');
            toggleButton.setIcon(new ImageIcon("img/show_password.png"));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignupView signupPage = new SignupView();
            signupPage.setVisible(true);
        });
    }
}
