package com.chknight.tasklist.controllers;

import com.chknight.tasklist.dtos.ToDoItemDTO;
import com.chknight.tasklist.services.ToDoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = { TodoController.class })
public class ToDoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ToDoService toDoService;

    @Test
    public void shouldReturnToDoItemDTOWhenCreateDataSuccessfully() throws Exception {
        String testString = "test todo text";
        ToDoItemDTO result = createToDoDTO(testString);

        when(toDoService.createToDoItem(testString)).thenReturn(result);

        mockMvc.perform(
                post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"text\": \"%s\"}", testString))
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.id", is(result.getId().intValue()))
        ).andExpect(
                jsonPath("$.text", is(result.getText()))
        ).andExpect(
                jsonPath("$.isCompleted", is(result.getIsCompleted()))
        );
    }

    private ToDoItemDTO createToDoDTO(String text) {
        return new ToDoItemDTO(
                1L,
                text,
                false,
                LocalDateTime.now()
        );
    }
}