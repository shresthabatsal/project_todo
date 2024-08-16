package controller;

import model.DashboardModel;
import view.DashboardView;
import view.TaskView;

public class DashboardController {
    private DashboardModel model;
    private DashboardView view;
    private TaskView todayTaskViewer;
    private TaskView importantTaskViewer;
    private TaskView upcomingTaskViewer;

    public DashboardController(DashboardModel model, DashboardView view) {
        this.model = model;
        this.view = view;
        initTaskView();
    }

    public void initTaskView() {
        this.todayTaskViewer = new TaskView(model.getTodayQuery(), view);
        this.importantTaskViewer = new TaskView(model.getImportantQuery(), view);
        this.upcomingTaskViewer = new TaskView(model.getUpcomingQuery(), view);

        view.addTaskView("Today", todayTaskViewer);
        view.addTaskView("Important", importantTaskViewer);
        view.addTaskView("Upcoming", upcomingTaskViewer);
    }

    public void refreshTaskView(String viewerName) {
        switch (viewerName) {
            case "Today":
                todayTaskViewer.refreshTasks();
                break;
            case "Important":
                importantTaskViewer.refreshTasks();
                break;
            case "Upcoming":
                upcomingTaskViewer.refreshTasks();
                break;
        }
    }
}