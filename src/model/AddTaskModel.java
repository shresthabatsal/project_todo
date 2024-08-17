package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import controller.AddTaskController;
import database.DatabaseConnection;
import view.util.UserSession;

public class AddTaskModel {

    private Connection conn;

    public AddTaskModel() {
        this.conn = DatabaseConnection.getConnection();
    }

    public void addTaskToDatabase(AddTaskController task) {
        String title = task.getTitle();
        String description = task.getDescription();
        Date dueDate = task.getDueDate();
        String repeat = task.getRepeatOption();
        String userId = UserSession.getUserId();

        // Format the date to a String
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dueDateString = dateFormat.format(dueDate);

        // Add task to database
        try {
            String sql = "INSERT INTO tasks (title, description, due_date, repeat_every, user_id, parent_task_id, is_important, is_complete) VALUES (?, ?, ?, ?, ?, ?, 0, 0)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setString(3, dueDateString);
                stmt.setString(4, repeat);
                stmt.setString(5, userId);
                stmt.setNull(6, java.sql.Types.INTEGER); // Initially set parent_task_id to NULL
                stmt.executeUpdate();

                // Retrieve the generated task ID
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int parentTaskId = generatedKeys.getInt(1);

                    // Update the task to set the parent_task_id
                    String updateSql = "UPDATE tasks SET parent_task_id = ? WHERE task_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, parentTaskId);
                        updateStmt.setInt(2, parentTaskId);
                        updateStmt.executeUpdate();
                    }

                    // Handle repeat logic
                    handleRepeatTask(task, userId, parentTaskId);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error adding task: " + ex.getMessage());
        }
    }

    // Method to handle repeat task logic
    private void handleRepeatTask(AddTaskController task, String userId, int parentTaskId) {
        String repeat = task.getRepeatOption();
        if ("Never".equals(repeat)) {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(task.getDueDate());

        switch (repeat) {
            case "Daily":
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case "Weekly":
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case "Monthly":
                calendar.add(Calendar.MONTH, 1);
                break;
            case "Yearly":
                calendar.add(Calendar.YEAR, 1);
                break;
        }

        Date nextDueDate = calendar.getTime();
        // Format the next due date to a String
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nextDueDateString = dateFormat.format(nextDueDate);

        AddTaskController nextTask = new AddTaskController(task.getTitle(), task.getDescription(), nextDueDate, task.getRepeatOption());

        try {
            String sql = "INSERT INTO tasks (title, description, due_date, repeat_every, user_id, parent_task_id, is_important, is_complete) VALUES (?, ?, ?, ?, ?, ?, 0, 0)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nextTask.getTitle());
                stmt.setString(2, nextTask.getDescription());
                stmt.setString(3, nextDueDateString);
                stmt.setString(4, nextTask.getRepeatOption());
                stmt.setString(5, userId);
                stmt.setInt(6, parentTaskId);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error adding repeat task: " + ex.getMessage());
        }
    }

    // Method to close the database connection when no longer needed
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}