package view;

import com.toedter.calendar.JDateChooser;
import controller.AddTaskController;
import controller.DashboardController;
import model.AddTaskModel;
import model.DashboardModel;
import view.util.FontLoad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddTaskView extends JPanel {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JDateChooser dateChooser;
    private JComboBox<String> repeatComboBox;
    private DashboardView dashboard;
    private DashboardController controller;
    private DashboardModel model;
    private TaskView taskView;
    private Runnable refreshRunnable;
    private AddTaskModel addTaskModel;

    public AddTaskView(DashboardView dashboard, Runnable refreshRunnable) {
        this.dashboard = dashboard;
        this.taskView = taskView;
        this.refreshRunnable = refreshRunnable;
        this.addTaskModel = new AddTaskModel();
        setBackground(Color.black);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(null);

        JPanel addTaskPanel = new JPanel() {
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
        addTaskPanel.setBounds(10, 0, 360, 700);
        addTaskPanel.setBackground(Color.WHITE);
        addTaskPanel.setLayout(null);

        // Create form components
        titleField = new JTextField();
        descriptionArea = new JTextArea();
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        // Load custom icon
        ImageIcon customIcon = new ImageIcon("img/due.png");
        dateChooser.setIcon(customIcon);

        // Make the date chooser uneditable
        JTextField dateEditor = ((JTextField) dateChooser.getDateEditor().getUiComponent());
        dateEditor.setEditable(false);
        dateEditor.setBackground(Color.WHITE);

        String[] repeatOptions = {"Daily", "Weekly", "Monthly", "Yearly", "Never"};
        repeatComboBox = new JComboBox<>(repeatOptions);
        repeatComboBox.setSelectedItem("Never");
        repeatComboBox.setBackground(Color.WHITE);

        // Set bounds for each component
        JLabel addTaskLabel = new JLabel("Add a task");
        addTaskLabel.setBounds(33, 25, 200, 35);
        addTaskLabel.setFont(FontLoad.getMontserratBold(20f));

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(32, 100, 100, 25);
        titleLabel.setFont(FontLoad.getMontserratBold(14f));
        titleField.setBounds(32, 125, 300, 40);
        titleField.setFont(FontLoad.getMontserratRegular(14f));

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(32, 175, 100, 25);
        descriptionLabel.setFont(FontLoad.getMontserratBold(14f));
        descriptionArea.setBounds(32, 200, 300, 80);
        descriptionArea.setFont(FontLoad.getMontserratRegular(14f));
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel dueDateLabel = new JLabel("Due Date");
        dueDateLabel.setBounds(32, 290, 100, 25);
        dueDateLabel.setFont(FontLoad.getMontserratBold(14f));
        dateChooser.setBounds(32, 315, 300, 40);
        dateChooser.setFont(FontLoad.getMontserratRegular(14f));
        dateChooser.setBackground(Color.WHITE);
        dateChooser.setDate(new Date());

        JLabel repeatLabel = new JLabel("Repeat");
        repeatLabel.setBounds(32, 365, 100, 25);
        repeatLabel.setFont(FontLoad.getMontserratBold(14f));
        repeatComboBox.setBounds(32, 390, 300, 40);
        repeatComboBox.setFont(FontLoad.getMontserratRegular(14f));
        repeatComboBox.setBackground(Color.WHITE);

        // Create save button
        JButton addButton = new JButton("Add task");
        addButton.setBounds(32, 480, 300, 40);
        addButton.setFont(FontLoad.getMontserratBold(16f));
        addButton.setBackground(Color.decode("#4682B4"));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();

                if (refreshRunnable != null) {
                    refreshRunnable.run(); // Trigger the refresh callback
                }
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(32, 540, 300, 40);
        clearButton.setFont(FontLoad.getMontserratBold(16f));
        clearButton.setBackground(Color.decode("#FFFFFF"));
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text fields
                titleField.setText("");
                descriptionArea.setText("");
            }
        });

        // Add components to the panel
        addTaskPanel.add(addTaskLabel);
        addTaskPanel.add(titleLabel);
        addTaskPanel.add(titleField);
        addTaskPanel.add(descriptionLabel);
        addTaskPanel.add(descriptionArea);
        addTaskPanel.add(dueDateLabel);
        addTaskPanel.add(dateChooser);
        addTaskPanel.add(repeatLabel);
        addTaskPanel.add(repeatComboBox);
        addTaskPanel.add(addButton);
        addTaskPanel.add(clearButton);

        add(addTaskPanel);
    }

    private void addTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        Date dueDate = dateChooser.getDate();
        String repeatOption = (String) repeatComboBox.getSelectedItem();

        if (title.isEmpty() || description.isEmpty() || dueDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        AddTaskController task = new AddTaskController(title, description, dueDate, repeatOption);
        addTaskModel.addTaskToDatabase(task);

        // Show success message
        JOptionPane.showMessageDialog(this, "Task added successfully!");

        // Reset fields
        resetFields();
    }

    private void resetFields() {
        titleField.setText("");
        descriptionArea.setText("");
        dateChooser.setDate(new Date());
        repeatComboBox.setSelectedItem("Never");
    }
}