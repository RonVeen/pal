package org.ninjaware.pal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ninjaware.pal.domain.Task;
import org.ninjaware.pal.domain.TaskStatus;
import org.ninjaware.pal.persistence.TaskPersister;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskPersister persister;
    private TaskService sut;

    @BeforeEach
    public void setup() {
        sut = new TaskService(persister);
    }

    @Test
    void getAllTasks() {
        var allTasks = List.of(Task.builder().description("feed cat").build());
        when(persister.getAllTasks()).thenReturn(allTasks);
        var resultList = sut.getAllTasks();
        assertThat(resultList).isNotNull();
        assertEquals(resultList.size(), allTasks.size());
        assertThat(resultList.size()).isEqualTo(allTasks.size());
    }

    @Test
    void getTask() {
        var taskId = UUID.randomUUID().toString();
        var task = Task.builder().taskID(taskId).description("feed cat").build();
        when(persister.getTask(taskId)).thenReturn(Optional.of(task));

        var result = sut.getTask(taskId);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getTaskID()).isEqualTo(task.getTaskID());
        assertThat(result.get().getDescription()).isEqualTo(task.getDescription());

        result = sut.getTask(UUID.randomUUID().toString());
        assertThat(result.isEmpty()).isTrue();
    }


    @ParameterizedTest
    @MethodSource("taskStatusInput")
    void completeUncompleteTask(String taskId, TaskStatus status) {
        var task = Task.builder().taskID(taskId).description("feed cat").build();
        when(persister.getTask(taskId)).thenReturn(Optional.of(task));
        when(persister.storeTask(task)).thenReturn(task);

        task = status == TaskStatus.Closed ? sut.completeTask(taskId) : sut.uncompleteTask(taskId);
        assertThat(task.getStatus()).isEqualTo(status);
        if (status == TaskStatus.Open) {
            assertThat(task.getCompletedOn()).isNull();
        } else {
            assertThat(task.getCompletedOn()).isNotNull();
        }

    }

    private static Stream<Arguments> taskStatusInput() {
        return Stream.of(
                Arguments.of(UUID.randomUUID().toString(), TaskStatus.Open),
                Arguments.of(UUID.randomUUID().toString(), TaskStatus.Closed)
        );
    }



    @ParameterizedTest
    @MethodSource("taskAssignmentInput")
    void assignUnassignTask(String taskId, String assignee) {
        var task = Task.builder().taskID(taskId).description("feed cat").build();
        when(persister.getTask(taskId)).thenReturn(Optional.of(task));
        when(persister.storeTask(task)).thenReturn(task);

        task = sut.assignTask(taskId, assignee);
        assertThat(task.getAssignedTo()).isEqualTo(assignee);
    }

    private static Stream<Arguments> taskAssignmentInput() {
        return Stream.of(
                Arguments.of(UUID.randomUUID().toString(), "SomeUser"),
                Arguments.of(UUID.randomUUID().toString(), null)
        );
    }

    @Test
    void saveTask() {
        var task = Task.builder().description("feed cat").build();
        when(persister.storeTask(task)).thenReturn(task);

        task = sut.storeTask(task);
        assertThat(task.getTaskID()).isNotNull();
    }

    @Test
    void updateTask() {
        var task = Task.builder().description("feed cat").build();
        when(persister.storeTask(task)).thenReturn(task);
        task = sut.storeTask(task);

        var updatedDescription = "New description";
        var updatedTask = Task.builder().taskID(task.getTaskID()).description(updatedDescription).build();
        when(persister.getTask(task.getTaskID())).thenReturn(Optional.of(updatedTask));
        when(persister.updateTask(updatedTask)).thenReturn(updatedTask);
        updatedTask = sut.updateTask(task.getTaskID(), updatedTask);

        assertThat(updatedTask.getDescription()).isEqualTo(updatedDescription);
    }



}