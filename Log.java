import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Log {

    public static void main(String[] args) {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 2));

        // Create labels and text fields
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        
        JButton loginButton = new JButton("Login");
        JLabel successLabel = new JLabel("");

        // Add components to the frame
        frame.add(userLabel);
        frame.add(userText);
        frame.add(passwordLabel);
        frame.add(passwordText);
        frame.add(loginButton);
        frame.add(successLabel);

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                
                // Simple hardcoded validation (just for example)
                if(username.equals("user") && password.equals("password123")) {
                    successLabel.setText("Login successful!");
                } else {
                    successLabel.setText("Login failed.");
                }
            }
        });

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}
