package org.ninjaware.pal.domain;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TaskTest {

    @Test
    public void testBuilder() {
       Task task = Task.builder().taskID(Task.newID()).description("Feed the cat").build();
       assertThat(task.getTaskID()).isNotNull();
        assertThat(task.getDescription()).isNotNull();
    }

}