package org.ninjaware.pal.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ninjaware.pal.domain.Task;
import org.ninjaware.pal.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(MockitoExtension.class)
public class TaskControllerStandaloneTest {

    private MockMvc mvc;
    private ObjectMapper mapper;

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController controller;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        mapper = new ObjectMapper();
    }


    @Test
    public void retrieveByIdWhenExists() throws Exception {
        var task = Task.builder().description("feed the cat").taskID(Task.newID()).build();

        //  given
        given(taskService.getTask("42")).willReturn(Optional.of(task));

        // when
        var response = mvc.perform(get("/task/42")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString()).isNotEmpty();
        var result = mapper.readValue(response.getContentAsString(), Task.class);
        assertThat(result.getDescription()).isEqualTo(task.getDescription());
        assertThat(result.getTaskID()).isEqualTo(task.getTaskID());
    }

    @Test
    public void retrieveByIdWhenNotExists() throws Exception {
        //  given
        given(taskService.getTask("666")).willReturn(Optional.empty());

        // when
        var response = mvc.perform(get("/task/666")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        assertThat(response.getContentAsString()).isNotEmpty();
        var result = mapper.readValue(response.getContentAsString(), HashMap.class);
        assertThat(result).isNotNull();
        assertThat(result.get("code")).isEqualTo("404");
    }



}
