package view;

import model.DashboardModel;
import view.util.CustomButton;
import view.util.FontLoad;
import controller.DashboardController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardView extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private CustomButton btnToday, btnImportant, btnUpcoming, btnSettings, btnLogout;
    private Color selectedColor = Color.decode("#E7E7E7");
    private JPanel editPanel;
    private JPanel addTaskPanel;
    private EditTaskView editTaskView;
    private AddTaskView addTaskView;
    private DashboardController controller;
    private DashboardView dashboard;
    private SettingsView settingsView;
    private TaskView taskView;

    public DashboardView() {
        // Frame properties
        setTitle("Dashboard");
        setSize(1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);

        JPanel menuPanel = createMenuPanel();

        // Card layout panel for different tabs
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBounds(335, 20, 620, 1000);
        cardPanel.setBackground(Color.WHITE);

        addTaskPanel = new JPanel();
        addTaskPanel.setBounds(980, 20, 400, 700);
        addTaskPanel.setBackground(Color.WHITE);
        addTaskPanel.setLayout(null);

        editPanel = new JPanel();
        editPanel.setBounds(980, 20, 400, 700);
        editPanel.setBackground(Color.WHITE);
        editPanel.setLayout(null);
        editPanel.setVisible(false);
        add(editPanel);

        DashboardModel model = new DashboardModel();
        controller = new DashboardController(model, this);

        // Settings panel
        cardPanel.add(new SettingsView(), "Settings");

        addTaskPanel.setVisible(true);
        add(addTaskPanel);

        setSelectedButton(btnToday);

        add(menuPanel);
        add(cardPanel);

        setVisible(true);
    }

    // Method to add TaskView to the card panel
    public void addTaskView(String name, TaskView view) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        String iconPath = getIconPath(name);
        setHeader(panel, name, iconPath, name.equals("Today"));
        panel.add(view);
        cardPanel.add(panel, name);
    }

    private String getIconPath(String name) {
        switch (name) {
            case "Today":
                return "img/today_big.png";
            case "Important":
                return "img/important_big.png";
            case "Upcoming":
                return "img/upcoming_big.png";
            default:
                return "";
        }
    }

    // Method to show the AddTaskView in the add task panel
    public void showAddTaskView(AddTaskView addTaskView) {
        addTaskPanel.removeAll();
        addTaskPanel.setBackground(Color.WHITE);
        addTaskView.setBounds(0, 0, 400, 700);
        addTaskPanel.add(addTaskView);
        addTaskPanel.setVisible(true);
        validate();
        repaint();
    }


    // Method to show the EditTaskView in the edit panel
    public void showEditTaskView(EditTaskView editTaskView) {
        editPanel.removeAll();
        editPanel.setBackground(Color.WHITE);
        editTaskView.setBounds(0, 0, 400, 700);
        editPanel.add(editTaskView);
        editPanel.setVisible(true);
        validate();
        repaint();
    }

    public void hideEditTaskView() {
        editPanel.setVisible(false);
        editPanel.removeAll();
        validate();
        repaint();
    }

    public void hideAddTaskPanel() {
        addTaskPanel.setVisible(false);
        addTaskPanel.removeAll();
        validate();
        repaint();
    }

    
    private void setHeader(JPanel panel, String heading, String iconPath, Boolean is_today){
        JLabel headingLabel = new JLabel(heading);
        headingLabel.setBounds(100, 20, 200, 50);
        headingLabel.setFont(FontLoad.getMontserratBold(30f));

        ImageIcon imageIcon = new ImageIcon(iconPath);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(15, 10, 64, 64);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy"); // Format the date as "MMM dd, yyyy"
        String dateStr = today.format(formatter);
        JLabel dateLabel = new JLabel(dateStr);
        dateLabel.setBounds(107, 60, 100, 30);
        dateLabel.setFont(FontLoad.getMontserratMedium(14f));

        if(is_today == true){
            panel.add(dateLabel);
        }

        panel.add(headingLabel);
        panel.add(imageLabel);
        panel.revalidate();
        panel.repaint();
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        menuPanel.setLayout(null);
        menuPanel.setBounds(20, 20, 300, 700);
        menuPanel.setBackground(new Color(0, 0, 0, 0)); // Ensure transparency for custom painting

        JLabel titleLabel = new JLabel("Menu");
        titleLabel.setBounds(30, 25, 100, 20);
        titleLabel.setFont(FontLoad.getMontserratBold(20f));
        menuPanel.add(titleLabel);

        // Tab buttons
        btnToday = createButton("Today", "img//today.png", 100);
        btnImportant = createButton("Important", "img//important.png", 145);
        btnUpcoming = createButton("Upcoming", "img//upcoming.png", 190);
        btnSettings = createButton("Settings", "img//settings.png", 550);
        btnLogout = createButton("Logout", null, 595);

        JButton button = new JButton("Logout");
        button.setBounds(20, 595, 260, 40);
        button.setBackground(Color.decode("#4682B4"));
        button.setForeground(Color.WHITE);
        button.setFont(FontLoad.getMontserratBold(16f));
        menuPanel.add(button);

        menuPanel.add(btnToday);
        menuPanel.add(btnImportant);
        menuPanel.add(btnUpcoming);
        menuPanel.add(btnSettings);
        menuPanel.add(btnLogout);

        return menuPanel;
    }

    private CustomButton createButton(String text, String iconPath, int yPosition) {
        ImageIcon icon = new ImageIcon(iconPath);
        CustomButton button = new CustomButton(text, icon);
        button.setBounds(20, yPosition, 260, 40);
        button.addActionListener(this);
        button.setFont(FontLoad.getMontserratRegular(16f));
        return button;
    }

    private void setSelectedButton(CustomButton selectedButton) {
        btnToday.setBackground(CustomButton.defaultColor);
        btnImportant.setBackground(CustomButton.defaultColor);
        btnUpcoming.setBackground(CustomButton.defaultColor);
        btnSettings.setBackground(CustomButton.defaultColor);
        btnLogout.setBackground(CustomButton.defaultColor);
        selectedButton.setBackground(selectedColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnToday) {
            controller.refreshTaskView("Today");
            cardLayout.show(cardPanel, "Today");
            setSelectedButton(btnToday);
            addTaskPanel.setVisible(true);
        } else if (e.getSource() == btnImportant) {
            controller.refreshTaskView("Important");
            cardLayout.show(cardPanel, "Important");
            setSelectedButton(btnImportant);
            addTaskPanel.setVisible(true);
        } else if (e.getSource() == btnUpcoming) {
            controller.refreshTaskView("Upcoming");
            cardLayout.show(cardPanel, "Upcoming");
            setSelectedButton(btnUpcoming);
            addTaskPanel.setVisible(true);
            setSelectedButton(btnUpcoming);
        } else if (e.getSource() == btnSettings) {
            cardLayout.show(cardPanel, "Settings");
            setSelectedButton(btnSettings);
            addTaskPanel.setVisible(false);
        } else if (e.getSource() == btnLogout) {
            System.exit(0);
        }
    }  

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardView::new);
    }
}