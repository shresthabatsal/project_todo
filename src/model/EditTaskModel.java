package model;

import database.DatabaseConnection;
import view.util.UserSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class EditTaskModel {
    private Connection conn;
    private Statement stmt;

    public EditTaskModel() {
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet getTask(int taskId) {
        try {
            String query = "SELECT title, description, due_date, repeat_every, parent_task_id FROM tasks WHERE task_id = " + taskId;
            return stmt.executeQuery(query);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updateTask(int taskId, String title, String description, Date dueDate, String repeatEvery) {
        try {
            if (stmt == null || stmt.isClosed()) {
                stmt = conn.createStatement();
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String userId = UserSession.getUserId();

            // Find parent task ID
            String getParentQuery = "SELECT parent_task_id FROM tasks WHERE task_id = " + taskId;
            ResultSet rs = stmt.executeQuery(getParentQuery);
            int parentTaskId = taskId;
            if (rs.next()) {
                int parentTaskIdFromDB = rs.getInt("parent_task_id");
                if (parentTaskIdFromDB != 0) {
                    parentTaskId = parentTaskIdFromDB;
                }
            }
            rs.close();

            // Update the parent task
            String updateQuery = "UPDATE tasks SET title = '" + title + "', description = '" + description + "', repeat_every = '" + repeatEvery + "', user_id = '" + userId + "' WHERE task_id = " + parentTaskId;
            stmt.executeUpdate(updateQuery);

            // Propagate the changes to all future instances
            updateFutureInstances(parentTaskId, title, description, dateFormat.format(dueDate), repeatEvery);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateFutureInstances(int parentTaskId, String title, String description, String dueDateString, String repeatEvery) {
        try {
            if (stmt == null || stmt.isClosed()) {
                stmt = conn.createStatement();
            }

            // Update all future instances with the same parent_task_id
            String updateQuery = "UPDATE tasks SET title = '" + title + "', description = '" + description + "', repeat_every = '" + repeatEvery + "' WHERE parent_task_id = " + parentTaskId + " AND due_date > CURDATE()";
            stmt.executeUpdate(updateQuery);

            handleRepeatingTasks(parentTaskId, title, description, dueDateString, repeatEvery);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleRepeatingTasks(int parentTaskId, String title, String description, String dueDateString, String repeatEvery) {
        try {
            if (stmt == null || stmt.isClosed()) {
                stmt = conn.createStatement();
            }

            String userId = UserSession.getUserId();

            if (repeatEvery != null && !repeatEvery.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                Date dueDate = dateFormat.parse(dueDateString);
                calendar.setTime(dueDate);

                // Delete existing future instances
                String deleteQuery = "DELETE FROM tasks WHERE parent_task_id = " + parentTaskId + " AND due_date > CURDATE()";
                stmt.executeUpdate(deleteQuery);

                // Insert new future instances based on the updated parent task details
                while (true) {
                    calendar.add(Calendar.DAY_OF_MONTH, getRepeatInterval(repeatEvery));
                    Date nextDueDate = calendar.getTime();

                    if (!nextDueDate.after(new Date())) {
                        continue;
                    }

                    String nextDueDateString = dateFormat.format(nextDueDate);
                    String insertQuery = "INSERT INTO tasks (title, description, due_date, repeat_every, user_id, parent_task_id) VALUES ('" 
                            + title + "', '" + description + "', '" + nextDueDateString + "', '" + repeatEvery + "', '" + userId + "', " + parentTaskId + ")";
                    stmt.executeUpdate(insertQuery);

                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int getRepeatInterval(String repeatOption) {
        switch (repeatOption.toLowerCase()) {
            case "daily":
                return 1;
            case "weekly":
                return 7;
            case "monthly":
                return 30;
            case "yearly":
                return 365;
            default:
                return 0;
        }
    }

    public void close() {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}