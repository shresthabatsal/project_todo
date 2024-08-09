package controller;

import model.EditTaskModel;

import java.sql.ResultSet;
import java.util.Date;

public class EditTaskController {
    private EditTaskModel model;

    public EditTaskController() {
        model = new EditTaskModel();
    }

    public ResultSet getTask(int taskId) {
        return model.getTask(taskId);
    }

    public void updateTask(int taskId, String title, String description, Date dueDate, String repeatEvery) {
        model.updateTask(taskId, title, description, dueDate, repeatEvery);
    }

    public void closeModel() {
        model.close();
    }
}