package org.ninjaware.pal.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskList {
    private String taskListID;
    private String description;
    private String details;
    private String createdBy;
    private LocalDateTime createdOn;
    private TaskStatus status;
    private LocalDateTime completedOn;
}
