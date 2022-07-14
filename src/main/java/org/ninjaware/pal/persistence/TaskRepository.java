package org.ninjaware.pal.persistence;

import org.ninjaware.pal.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    Optional<TaskEntity> findByTaskID(String taskId);


}
