package org.ninjaware.pal.service;

import org.ninjaware.pal.TaskEntity;
import org.ninjaware.pal.domain.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskAssembler {

    public TaskEntity toEntity(Task task) {
        return TaskEntity.builder().assignedTo(task.getAssignedTo())
                .completedBy(task.getCompletedBy())
                .completedOn(task.getCompletedOn())
                .createdBy(task.getCreatedBy())
                .createdOn(task.getCreatedOn())
                .details(task.getDetails())
                .description(task.getDescription())
                .id(task.getId())
                .status(task.getStatus())
                .taskID(task.getTaskID())
                .taskListID(task.getTaskListID())
                .build();
    }

    public Task toDto(TaskEntity entity) {
        return Task.builder().assignedTo(entity.getAssignedTo())
                .completedBy(entity.getCompletedBy())
                .completedOn(entity.getCompletedOn())
                .createdBy(entity.getCreatedBy())
                .createdOn(entity.getCreatedOn())
                .details(entity.getDetails())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .taskID(entity.getTaskID())
                .taskListID(entity.getTaskListID())
                .build();
    }
}
