package org.ninjaware.pal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ninjaware.pal.domain.TaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "task_id")
    private String taskID;

    @Column
    private String description;

    @Column
    private String details;

    @Column(name = "tasklist_id")
    private String taskListID;

    @Column
    private String createdBy;

    @Column
    private LocalDateTime createdOn;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column
    private String assignedTo;

    @Column
    private String completedBy;

    @Column
    private LocalDateTime completedOn;


}
