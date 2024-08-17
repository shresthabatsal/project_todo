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

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}

