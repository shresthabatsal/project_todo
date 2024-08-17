package controller;

import java.util.Date;

public class AddTaskController {
    private String title;
    private String description;
    private Date dueDate;
    private String repeatOption;

    public AddTaskController(String title, String description, Date dueDate, String repeatOption) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.repeatOption = repeatOption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getRepeatOption() {
        return repeatOption;
    }

    public void setRepeatOption(String repeatOption) {
        this.repeatOption = repeatOption;
    }
}