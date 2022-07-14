package org.ninjaware.pal.service;

import org.ninjaware.pal.domain.Task;
import org.ninjaware.pal.domain.TaskList;

public interface TaskListService {
    TaskList createTaskList(String description, String details, String user);
    TaskList updateTaskList(String taskListID, String description, String details, String user);
    TaskList removeTaskList(String taskListID, String user);
    Task createTask(String description, String details, String createUser, String assignedUser);
    Task updateTask(String taskID, String description, String details, String user);
    Task completeTask(String taskID, boolean complete, String user);
    Task removeTask(String taskID, String user);
}
