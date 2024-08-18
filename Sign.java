import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sign {

    public static void main(String[] args) {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Sign Up Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        // Create labels and text fields
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailText = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordText = new JPasswordField();
        
        JButton signUpButton = new JButton("Sign Up");
        JLabel successLabel = new JLabel("");

        // Add components to the frame
        frame.add(userLabel);
        frame.add(userText);
        frame.add(emailLabel);
        frame.add(emailText);
        frame.add(passwordLabel);
        frame.add(passwordText);
        frame.add(confirmPasswordLabel);
        frame.add(confirmPasswordText);
        frame.add(signUpButton);
        frame.add(successLabel);

        // Action listener for the sign-up button
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());
                
                // Simple validation (more checks can be added)
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    successLabel.setText("Please fill in all fields.");
                } else if (!password.equals(confirmPassword)) {
                    successLabel.setText("Passwords do not match.");
                } else {
                    // Simulate storing user data (just for example)
                    successLabel.setText("Sign Up successful!");
                    // Here, you would typically save the user data to a database
                }
            }
        });

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}

