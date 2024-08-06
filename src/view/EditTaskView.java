package view;

import com.toedter.calendar.JDateChooser;
import controller.EditTaskController;
import model.EditTaskModel;
import view.util.FontLoad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTaskView extends JPanel {
    private int taskId;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JDateChooser dateChooser;
    private JComboBox<String> repeatComboBox;
    private Runnable refreshRunnable;
    private DashboardView dashboard;
    private EditTaskController controller;
    private TaskView taskView;

    public EditTaskView(int taskId, Runnable refreshRunnable, DashboardView dashboard) {
        this.taskId = taskId;
        this.refreshRunnable = refreshRunnable;
        this.dashboard = dashboard;
        this.controller = new EditTaskController();
        initializeComponents();
    }

    private void initializeComponents(){
        setLayout(null);
        JPanel editTaskPanel = new JPanel() {
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
        editTaskPanel.setBounds(10, 0, 360, 700);
        editTaskPanel.setBackground(Color.WHITE);
        editTaskPanel.setLayout(null);

        // Fetch the task data from the database
        try {
            ResultSet rs = controller.getTask(taskId);

            if (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                String dueDate = rs.getString("due_date");
                String repeatEvery = rs.getString("repeat_every");

                titleField = new JTextField(title, 20);
                descriptionArea = new JTextArea(description, 5, 20);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(dueDate);
                dateChooser = new JDateChooser(date);
                dateChooser.setDateFormatString("yyyy-MM-dd");

                ImageIcon customIcon = new ImageIcon("img//due.png");
                dateChooser.setIcon(customIcon);

                // Make the date chooser uneditable
                JTextField dateEditor = ((JTextField) dateChooser.getDateEditor().getUiComponent());
                dateEditor.setEditable(false);
                dateEditor.setBackground(Color.WHITE);

                String[] repeatOptions = {"Daily", "Weekly", "Monthly", "Yearly", "Never"};
                repeatComboBox = new JComboBox<>(repeatOptions);
                repeatComboBox.setSelectedItem(repeatEvery);
                repeatComboBox.setBackground(Color.WHITE);

                JLabel editTaskLabel = new JLabel("Edit task");
                editTaskLabel.setBounds(33, 25, 100, 35);
                editTaskLabel.setFont(FontLoad.getMontserratBold(20f));

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

                JLabel repeatLabel = new JLabel("Repeat");
                repeatLabel.setBounds(32, 365, 100, 25);
                repeatLabel.setFont(FontLoad.getMontserratBold(14f));
                repeatComboBox.setBounds(32, 390, 300, 40);
                repeatComboBox.setFont(FontLoad.getMontserratRegular(14f));
                repeatComboBox.setBackground(Color.WHITE);

                JButton saveButton = new JButton("Save");
                saveButton.setBounds(32, 480, 300, 40);
                saveButton.setFont(FontLoad.getMontserratBold(16f));
                saveButton.setBackground(Color.decode("#4682B4"));
                saveButton.setForeground(Color.WHITE);
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Update the task in the database
                        try {
                            controller.updateTask(taskId, titleField.getText(), descriptionArea.getText(), dateChooser.getDate(), (String) repeatComboBox.getSelectedItem());
                            JOptionPane.showMessageDialog(EditTaskView.this, "Task updated successfully!");
                            // Call the refresh function
                            if (refreshRunnable != null) {
                                refreshRunnable.run(); 
                            }
                            dashboard.hideEditTaskView();
                            
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBounds(32, 540, 300, 40);
                cancelButton.setFont(FontLoad.getMontserratBold(16f));
                cancelButton.setBackground(Color.decode("#FFFFFF"));
                cancelButton.setForeground(Color.BLACK);
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dashboard.hideEditTaskView();
                    }
                });

                editTaskPanel.add(editTaskLabel);
                editTaskPanel.add(titleLabel);
                editTaskPanel.add(titleField);
                editTaskPanel.add(descriptionLabel);
                editTaskPanel.add(descriptionArea);
                editTaskPanel.add(dueDateLabel);
                editTaskPanel.add(dateChooser);
                editTaskPanel.add(repeatLabel);
                editTaskPanel.add(repeatComboBox);
                editTaskPanel.add(saveButton);
                editTaskPanel.add(cancelButton);
            }

            rs.close();
            controller.closeModel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        add(editTaskPanel);
    }
}