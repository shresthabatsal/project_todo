package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.TaskController;
import database.DatabaseConnection;
import view.util.UserSession;

public class TaskModel {
    private String sqlQuery;
    private String userId;
    private Connection conn;

    public TaskModel(String sqlQuery) {
        this.sqlQuery = sqlQuery;
        this.userId = UserSession.getUserId();
        this.conn = DatabaseConnection.getConnection();
    }

    // Fetch tasks from the database
    public List<TaskController> fetchTasks() {
        List<TaskController> tasks = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setString(1, userId); // Bind user ID to the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int taskId = rs.getInt("task_id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    LocalDate dueDate = LocalDate.parse(rs.getString("due_date"));
                    String repeatEvery = rs.getString("repeat_every");
                    boolean isImportant = rs.getBoolean("is_important");

                    TaskController task = new TaskController(taskId, title, description, dueDate, repeatEvery, isImportant);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // Update task completion status
    public void updateTaskCompletion(int taskId) {
        String updateQuery = "UPDATE tasks SET is_complete = true WHERE task_id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, taskId);
            stmt.setString(2, userId); // Bind user ID to the query
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update task importance status
    public void updateTaskImportance(int taskId, boolean isImportant) {
        String updateQuery = "UPDATE tasks SET is_important = ? WHERE task_id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setBoolean(1, isImportant);
            stmt.setInt(2, taskId);
            stmt.setString(3, userId); // Bind user ID to the query
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Delete a task and its future instances from the database
public void deleteTask(int taskId) {
    String getParentQuery = "SELECT parent_task_id FROM tasks WHERE task_id = ?";
    String deleteQuery = "DELETE FROM tasks WHERE (task_id = ? OR parent_task_id = ?) AND user_id = ?";
    
    try (PreparedStatement getParentStmt = conn.prepareStatement(getParentQuery);
         PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

        // Retrieve the parent task ID
        getParentStmt.setInt(1, taskId);
        ResultSet rs = getParentStmt.executeQuery();
        int parentTaskId = taskId; // Default to the task ID itself
        if (rs.next()) {
            parentTaskId = rs.getInt("parent_task_id");
        }

        // Delete the task and its future instances
        deleteStmt.setInt(1, taskId);
        deleteStmt.setInt(2, parentTaskId);
        deleteStmt.setString(3, UserSession.getUserId()); // Bind user ID to the query
        deleteStmt.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Close the database connection when no longer needed
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