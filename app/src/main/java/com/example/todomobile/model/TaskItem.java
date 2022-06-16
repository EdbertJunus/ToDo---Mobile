package com.example.todomobile.model;

import com.google.firebase.Timestamp;

public class TaskItem {
    private String TaskId;
    private String TaskName;
    private String TaskDescription;
    private Timestamp TaskDateTime;
    private String UserId;

    public TaskItem() {
    }

    public TaskItem(String taskId, String taskName, String taskDescription, Timestamp taskDateTime, String userId) {
        TaskId = taskId;
        TaskName = taskName;
        TaskDescription = taskDescription;
        TaskDateTime = (Timestamp) taskDateTime;
        UserId = userId;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    public Timestamp getTaskDateTime() {
        return TaskDateTime;
    }

    public void setTaskDateTime(Timestamp taskDateTime) {
        TaskDateTime = taskDateTime;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
