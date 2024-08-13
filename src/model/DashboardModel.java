package model;

public class DashboardModel {

    public String getTodayQuery() {
        return "SELECT * FROM tasks WHERE user_id = ? AND due_date = CURDATE() AND is_complete = 0";
    }

    public String getImportantQuery() {
        return "SELECT * FROM tasks WHERE user_id = ? AND is_important = true AND is_complete = 0";
    }

    public String getUpcomingQuery() {
        return "SELECT * FROM tasks WHERE user_id = ? AND due_date > CURDATE() AND is_complete = 0";
    }
}