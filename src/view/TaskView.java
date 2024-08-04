package view;

import model.TaskModel;
import view.util.FontLoad;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import controller.TaskController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskView extends JPanel {

    private JPanel taskContainer;
    private String sqlQuery;
    private EditTaskView editTaskView;
    private DashboardView dashboard;
    private TaskModel taskModel;
    public Object titleLabel;
    private JButton refreshButton;

    public TaskView(String sqlQuery, DashboardView dashboard) {
        this.sqlQuery = sqlQuery;
        this.dashboard = dashboard;
        this.taskModel = new TaskModel(sqlQuery);
        setLayout(null);
        setBounds(5, 60, 650, 660);
        setBackground(Color.WHITE);

        AddTaskView addTaskView = new AddTaskView(dashboard, TaskView.this::refreshTasks);
        addTaskView.setBackground(Color.WHITE);
        dashboard.showAddTaskView(addTaskView);
        dashboard.hideEditTaskView();


        refreshButton = new JButton(new ImageIcon("img/refresh.png"));
        refreshButton.setBounds(575, 0, 35, 40);
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setBorder(BorderFactory.createEmptyBorder());
        refreshButton.setVisible(true);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTasks();
            }
        });
        add(refreshButton);

        initializeTaskContainer();
        fetchAndDisplayTasks();
    }

    private void initializeTaskContainer() {
        // Create a panel to hold the task data
        taskContainer = new JPanel();
        taskContainer.setLayout(null);
        taskContainer.setBackground(Color.WHITE);
        taskContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        taskContainer.setBounds(0, 150, 602, 10);
        taskContainer.setPreferredSize(new Dimension(602, 10)); // Initial height set to 10 to start with

        // Customize the scrollbar
        UIManager.put("ScrollBar.thumb", Color.decode("#FFFFFF"));
        UIManager.put("ScrollBar.thumbHighlight", Color.decode("#FFFFFF"));
        UIManager.put("ScrollBar.thumbDarkShadow", Color.decode("#FFFFFF"));
        UIManager.put("ScrollBar.thumbShadow", Color.decode("#FFFFFF"));

        // Scroll pane to make taskContainer scrollable
        JScrollPane scrollPane = new JScrollPane(taskContainer);
        scrollPane.setBounds(10, 40, 602, 600);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#BFBFBF");
                this.trackColor = Color.decode("#D5D5D5");
            }
        });

        add(scrollPane);
    }

    private void fetchAndDisplayTasks() {
        List<TaskController> tasks = taskModel.fetchTasks();

        // Coordinates for positioning each taskPanel
        int panelY = 20;
        int panelHeight = 100;

        // Clear existing task panels
        taskContainer.removeAll();

        // Iterate through the tasks and add to the panel
        for (TaskController task : tasks) {
            JPanel taskPanel = createTaskPanel(task, panelY, panelHeight);
            taskContainer.add(taskPanel);

            // Update y-coordinate for the next task panel
            panelY += panelHeight + 10;
        }

        // Update the preferred size of the task container to fit all tasks
        taskContainer.setPreferredSize(new Dimension(650, panelY));
        taskContainer.revalidate();
        taskContainer.repaint();
    }

    private JPanel createTaskPanel(TaskController task, int panelY, int panelHeight) {
        // Create labels and buttons for task panel
        JLabel titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(FontLoad.getMontserratBold(16f));
        titleLabel.setBounds(60, 20, 500, 30);

        JLabel descriptionLabel = new JLabel("Description: " + task.getDescription());
        descriptionLabel.setFont(FontLoad.getMontserratRegular(12f));
        descriptionLabel.setBounds(60, 40, 540, 20);

        JLabel dueDateLabel = new JLabel("Due Date: " + task.getFormattedDueDate());
        dueDateLabel.setFont(FontLoad.getMontserratRegular(12f));
        dueDateLabel.setBounds(60, 55, 270, 20);

        JLabel repeatEveryLabel = new JLabel(" | " + "Repeat: " + task.getRepeatEvery());
        repeatEveryLabel.setFont(FontLoad.getMontserratRegular(12f));
        repeatEveryLabel.setBounds(200, 55, 270, 20);

        JButton doneButton = new JButton(new ImageIcon("img//empty_checkbox.png"));
        doneButton.setBackground(Color.WHITE);
        doneButton.setPreferredSize(new Dimension(30, 30));
        doneButton.setBounds(10, 35, 30, 30);
        doneButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JButton importantButton = new JButton(new ImageIcon(task.isImportant() ? "img//important_yes.png" : "img//important.png"));
        importantButton.setBackground(Color.WHITE);
        importantButton.setPreferredSize(new Dimension(30, 30));
        importantButton.setBounds(480, 35, 30, 30);
        importantButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JButton moreOptionsButton = new JButton(new ImageIcon("img//more.png"));
        moreOptionsButton.setBackground(Color.WHITE);
        moreOptionsButton.setPreferredSize(new Dimension(30, 30));
        moreOptionsButton.setBounds(520, 35, 30, 30);
        moreOptionsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Create the popup menu and menu items
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBackground(Color.WHITE);
        JMenuItem editMenuItem = new JMenuItem("Edit");
        editMenuItem.setBackground(Color.WHITE);
        editMenuItem.setFont(FontLoad.getMontserratMedium(14f));
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.setBackground(Color.WHITE);
        deleteMenuItem.setFont(FontLoad.getMontserratMedium(14f));
        popupMenu.add(editMenuItem);
        popupMenu.add(deleteMenuItem);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(null);
        taskPanel.setBackground(Color.WHITE);
        taskPanel.setBorder(new LineBorder(Color.BLACK, 1, true));
        taskPanel.setBounds(20, panelY, 560, panelHeight);

        taskPanel.add(titleLabel);
        taskPanel.add(descriptionLabel);
        taskPanel.add(dueDateLabel);
        taskPanel.add(repeatEveryLabel);
        taskPanel.add(doneButton);
        taskPanel.add(importantButton);
        taskPanel.add(moreOptionsButton);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Change button image to checked checkbox
                doneButton.setIcon(new ImageIcon("img//checked_checkbox.png"));
                titleLabel.setForeground(Color.LIGHT_GRAY);
                descriptionLabel.setForeground(Color.LIGHT_GRAY);
                dueDateLabel.setForeground(Color.LIGHT_GRAY);
                repeatEveryLabel.setForeground(Color.LIGHT_GRAY);

                // Update task completion status
                taskModel.updateTaskCompletion(task.getTaskId());

                // Schedule task removal after 5 seconds
                Timer timer = new Timer(2500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        taskContainer.remove(taskPanel);
                        repositionPanels();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        importantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle importance
                boolean newImportance = !task.isImportant();
                taskModel.updateTaskImportance(task.getTaskId(), newImportance);
                // Refresh task viewer after update
                refreshTasks();
            }
        });

        moreOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(moreOptionsButton, 30, 0);
            }
        });
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        TaskView.this,
                        "Are you sure you want to delete this task?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
        
                if (option == JOptionPane.YES_OPTION) {
                    taskModel.deleteTask(task.getTaskId());
                    refreshTasks();
                }
            }
        });
        editMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditTaskView editTaskView = new EditTaskView(task.getTaskId(), TaskView.this::refreshTasks, dashboard);
                editTaskView.setBackground(Color.WHITE);
                dashboard.showEditTaskView(editTaskView);
            }
        });

        return taskPanel;
    }

    // Method to refresh tasks by fetching data from the database again
    public void refreshTasks() {
        fetchAndDisplayTasks();
    }

    private void repositionPanels() {
        Component[] components = taskContainer.getComponents();
        int panelY = 20;
        for (Component component : components) {
            component.setBounds(20, panelY, 560, 100);
            panelY += 110;
        }
        taskContainer.setPreferredSize(new Dimension(650, panelY));
        taskContainer.revalidate();
        taskContainer.repaint();
    }
}