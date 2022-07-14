package org.ninjaware.pal.rest;

import org.ninjaware.pal.domain.Task;
import org.ninjaware.pal.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("task")
@Validated
public class TaskController {

    public TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public @ResponseBody List<Task> getAll() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public @ResponseBody Task get(@PathVariable String taskId) {
        Optional<Task> task = taskService.getTask(taskId);
        if (task.isEmpty()) {
            throw new NotFoundException(taskId);
        }
        return task.get();
    }

    @PostMapping("/")
    public @ResponseBody Task create(@Valid @RequestBody Task task) {
        return taskService.storeTask(task);
    }


    @PutMapping("/{taskId}")
    public @ResponseBody Task update(@PathVariable String taskId, @Valid @RequestBody Task task) {
        var updated = taskService.updateTask(taskId, task);
        if (updated == null) {
            throw new NotFoundException(taskId);
        }
        return updated;
    }


    @DeleteMapping("/{taskId}")
    public @ResponseBody Task delete(@PathVariable String taskId) {
        var deleted = taskService.removeTask(taskId);
        if (deleted == null) {
            throw new NotFoundException(taskId);
        }
        return deleted;
    }


    @PutMapping("/{taskId}/complete")
    public @ResponseBody Task completeTask(@PathVariable String taskId) {
        var task = taskService.completeTask(taskId);
        if (task == null) {
            throw new NotFoundException(taskId);
        }
        return task;
    }


    @PutMapping("/{taskId}/uncomplete")
    public @ResponseBody Task uncompleteTask(@PathVariable String taskId) {
        var task = taskService.uncompleteTask(taskId);
        if (task == null) {
            throw new NotFoundException(taskId);
        }
        return task;
    }

    @PutMapping("/{taskId}/assign/{assignee}")
    public @ResponseBody Task assignTask(@PathVariable String taskId, @PathVariable String assignee) {
        var task = taskService.assignTask(taskId, assignee);
        if (task == null) {
            throw new NotFoundException(taskId);
        }
        return task;
    }

    @PutMapping("/{taskId}/unassign")
    public @ResponseBody Task unassignTask(@PathVariable String taskId) {
        var task = taskService.assignTask(taskId, null);
        if (task == null) {
            throw new NotFoundException(taskId);
        }
        return task;
    }


}
