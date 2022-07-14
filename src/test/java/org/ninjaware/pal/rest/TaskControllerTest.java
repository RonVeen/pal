package org.ninjaware.pal.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.ninjaware.pal.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test_findAll_emptyResponse() throws Exception {
        this.mockMvc.perform(get("/task/")).andExpect(status().isOk());
    }

    @Test
    void test_findOne_returnsNotFound() throws Exception {
        this.mockMvc.perform(get("/task/1")).andExpect(status().isNotFound());
    }

    @Test
    void test_create_task() throws Exception {
        Task task = Task.builder().description("feed the cat").build();
        MvcResult result = mockMvc.perform(post("/task/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(task))).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        var resultTask = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class);
        assertThat(resultTask.getTaskID()).isNotNull();
    }



}