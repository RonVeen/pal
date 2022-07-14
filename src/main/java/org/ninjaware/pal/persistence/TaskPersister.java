package org.ninjaware.pal.persistence;

import lombok.AllArgsConstructor;
import org.ninjaware.pal.TaskEntity;
import org.ninjaware.pal.domain.Task;
import org.ninjaware.pal.service.TaskAssembler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class TaskPersister {
    private final TaskRepository repository;
    private final TaskAssembler assembler;

    public List<Task> getAllTasks() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(assembler::toDto)
                .toList();
    }

    public Optional<Task> getTask(String taskId) {
        Optional<TaskEntity> entity = repository.findByTaskID(taskId);
        return entity.map(assembler::toDto);
    }

    public Task storeTask(Task task) {
        var entity = repository.findByTaskID(task.getTaskID());
        entity.ifPresent(taskEntity -> task.setId(taskEntity.getId()));
        return assembler.toDto(repository.save(assembler.toEntity(task)));
    }

    public Task updateTask(Task task) {
        var entity = repository.findByTaskID(task.getTaskID());
        entity.ifPresent(taskEntity -> task.setId(taskEntity.getId()));
        return assembler.toDto(repository.save(assembler.toEntity(task)));
    }

    public Task removeTask(String taskId) {
        var existing = repository.findByTaskID(taskId);
        if (existing.isEmpty()) {
            return null;
        }
        var entity = existing.get();
        repository.delete(entity);
        return assembler.toDto(entity);
    }


}
