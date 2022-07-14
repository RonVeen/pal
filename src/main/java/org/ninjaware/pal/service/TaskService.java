package org.ninjaware.pal.service;

import lombok.AllArgsConstructor;
import org.ninjaware.pal.domain.Task;
import org.ninjaware.pal.domain.TaskStatus;
import org.ninjaware.pal.persistence.TaskPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskPersister persister;

    public List<Task> getAllTasks() {
        return persister.getAllTasks();
    }

    public Optional<Task> getTask(String taskId) {
        return persister.getTask(taskId);
    }

    public Task storeTask(Task task) {
        Task.assignTaskId(task);
        task.setCreatedOn(LocalDateTime.now());
        task.setStatus(TaskStatus.Open);
        return persister.storeTask(task);
    }

    public Task updateTask(String taskId, Task task) {
        var existing = persister.getTask(taskId);
        if (existing.isEmpty()) {
            return null;
        }
        var existingTask = existing.get();
        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.getDetails() != null){
            existingTask.setDetails(task.getDetails());
        }
        return persister.updateTask(existingTask);
    }


    public Task removeTask(String taskId) {
        return persister.removeTask(taskId);
    }


    public Task completeTask(String taskId) {
        return internalCompleteTask(taskId, true);
    }

    public Task uncompleteTask(String taskId) {
        return internalCompleteTask(taskId, false);
    }

    public Task assignTask(String taskId, String assignee) {
        var existing = persister.getTask(taskId);
        if (existing.isEmpty()) {
            return null;
        }
        var task = existing.get();
        task.setAssignedTo(assignee);
        return persister.storeTask(task);
    }


    private Task internalCompleteTask(String taskId, boolean complete) {
        var existing = persister.getTask(taskId);
        if (existing.isEmpty()) {
            return null;
        }
        var task = existing.get();
        task.setStatus(complete ? TaskStatus.Closed : TaskStatus.Open);
        if (complete) {
            task.setStatus(TaskStatus.Closed);
            task.setCompletedOn(LocalDateTime.now());
        } else {
            task.setStatus(TaskStatus.Open);
            task.setCompletedOn(null);
        }
        return persister.storeTask(task);
    }

}
