package org.ninjaware.pal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @JsonIgnore private Long id;
    private String taskID;
    @NonNull private String description;
    private String details;
    private String taskListID;
    private String createdBy;
    private LocalDateTime createdOn;
    private TaskStatus status;
    private String assignedTo;
    private String completedBy;
    private LocalDateTime completedOn;

    public static String newID() {
        return UUID.randomUUID().toString();
    }

    public static void assignTaskId(Task task) {
        if (task.getTaskID() == null) {
            task.setTaskID(Task.newID());
        }
    }
}
