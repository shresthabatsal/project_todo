package view;

import controller.LoginController;
import view.util.FontLoad;
import view.util.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();
        
        setBounds(0, 0, 1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImagePanel imagePanel = new ImagePanel("img/to_do.png", 900, 700);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(970, 20, 350, 700);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1)));

        imagePanel.setBounds(40, 20, 900, 700);

        add(formPanel);
        add(imagePanel);

        JPanel focusPanel = new JPanel();
        focusPanel.setBounds(0, 0, 1, 1);
        focusPanel.setFocusable(true);
        add(focusPanel);

        setVisible(true);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setBounds(30, 130, 100, 50);
        titleLabel.setFont(FontLoad.getMontserratBold(23f));
        formPanel.add(titleLabel);

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Log In");

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(25, 205, 100, 25);
        emailLabel.setFont(FontLoad.getMontserratMedium(14f));
        emailField.setFont(FontLoad.getMontserratRegular(15f));
        emailField.setBounds(25, 230, 300, 40);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(25, 280, 100, 25);
        passwordLabel.setFont(FontLoad.getMontserratMedium(14f));
        passwordField.setBounds(25, 305, 270, 40);
        passwordField.setFont(FontLoad.getMontserratRegular(15f));

        loginButton.setBounds(25, 370, 300, 40);
        loginButton.setBackground(Color.decode("#4682B4"));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(FontLoad.getMontserratBold(15f));

        JButton togglePasswordButton = new JButton(new ImageIcon("img/show_password.png"));
        togglePasswordButton.setBounds(295, 305, 30, 40);
        togglePasswordButton.setBackground(Color.WHITE);
        togglePasswordButton.addActionListener(e -> togglePasswordVisibility(passwordField, togglePasswordButton));

        JLabel newAccountLabel = new JLabel("Don't have an account?");
        newAccountLabel.setBounds(40, 420, 300, 30);
        newAccountLabel.setFont(FontLoad.getMontserratMedium(16f));
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(240, 424, 70, 20);
        signupButton.setBackground(Color.WHITE);
        signupButton.setFont(FontLoad.getMontserratBold(16f));
        signupButton.setBorder(BorderFactory.createEmptyBorder());
        signupButton.addActionListener(e -> {
            SignupView signupPage = new SignupView();
            signupPage.setVisible(true);
            dispose();
        });

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(loginButton);
        formPanel.add(newAccountLabel);
        formPanel.add(signupButton);
        formPanel.add(togglePasswordButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
        
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(formPanel, "Please fill in all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            } else if (!loginController.isValidEmail(email)) {
                JOptionPane.showMessageDialog(formPanel, "Please enter a valid email address.", "Invalid Email", JOptionPane.WARNING_MESSAGE);
            } else if (loginController.login(email, password)) {
                JOptionPane.showMessageDialog(formPanel, "Login Successful");
                new DashboardView();
                dispose();
            } else {
                JOptionPane.showMessageDialog(formPanel, "Invalid email or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
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
        SwingUtilities.invokeLater(LoginView::new);
    }
}

class ImagePanel extends JPanel {
    private Image image;
    private int panelWidth;
    private int panelHeight;

    public ImagePanel(String imagePath, int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            image = imageIcon.getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, panelWidth, panelHeight);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(1, 1, panelWidth - 2, panelHeight - 2, 50, 50);

        if (image != null) {
            int x = (panelWidth - image.getWidth(this)) / 2;
            int y = (panelHeight - image.getHeight(this)) / 2;
            g2.drawImage(image, x, y, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(panelWidth, panelHeight);
    }
}