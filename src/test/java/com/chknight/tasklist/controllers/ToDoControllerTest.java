package com.chknight.tasklist.controllers;

import com.chknight.tasklist.dtos.ToDoItemDTO;
import com.chknight.tasklist.exceptions.ToDoItemNotFoundException;
import com.chknight.tasklist.exceptions.ToDoItemValidationException;
import com.chknight.tasklist.services.ToDoService;
import com.chknight.tasklist.shared.ErrorDetailMessage;
import com.chknight.tasklist.shared.GenericErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void shouldReturn400IfInputIsTooShortWhenCreatingToDoItem() throws Exception {
        String testString = "";

        mockInvalidException(testString);

        mockMvc.perform(
                post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"text\": \"%s\"}", testString))
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
                jsonPath("$.name", is("ValidationError"))
        ).andExpect(
                jsonPath("$.details[0].location", is("params"))
        ).andExpect(
                jsonPath("$.details[0].param", is("text"))
        ).andExpect(
                jsonPath("$.details[0].msg", is("Must be between 1 and 50 chars long"))
        ).andExpect(
                jsonPath("$.details[0].value", is(""))
        );
    }

    @Test
    public void shouldReturn400IfInputIsTooLongWhenCreatingToDoItem() throws Exception {
        String testString = "a".repeat(51);

        mockInvalidException(testString);

        mockMvc.perform(
                post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"text\": \"%s\"}", testString))
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
                jsonPath("$.name", is("ValidationError"))
        ).andExpect(
                jsonPath("$.details[0].location", is("params"))
        ).andExpect(
                jsonPath("$.details[0].param", is("text"))
        ).andExpect(
                jsonPath("$.details[0].msg", is("Must be between 1 and 50 chars long"))
        ).andExpect(
                jsonPath("$.details[0].value", is(""))
        );
    }

    @Test
    public void shouldReturnToDoItemDTOWhenFindingDataSuccessfully() throws Exception {
        Long id = 1L;
        ToDoItemDTO result = createToDoDTO("test");

        when(toDoService.findToDoItem(id)).thenReturn(result);

        mockMvc.perform(
                get("/todo/{id}", id)
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

    @Test
    public void shouldReturn404IfCouldNotFoundById() throws Exception {
        Long id = 1L;
        mockNotFoundException(id);

        mockMvc.perform(
                get("/todo/{id}", id)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$.name", is("NotFoundError"))
        ).andExpect(
                jsonPath("$.details[0].message", is("Item with 1 not found"))
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

    private void mockInvalidException(String text) throws ToDoItemValidationException {
        when(toDoService.createToDoItem(text)).thenThrow(
                new ToDoItemValidationException(
                        List.of(
                                ErrorDetailMessage.build(
                                        "params",
                                        "text",
                                        "Must be between 1 and 50 chars long",
                                        ""
                                )
                        )
                )
        );
    }

    private void mockNotFoundException(Long id) throws ToDoItemNotFoundException {
        when(toDoService.findToDoItem(id)).thenThrow(
                new ToDoItemNotFoundException(
                        List.of(
                                GenericErrorMessage.build(
                                        String.format("Item with %d not found", id)
                                )
                        )
                )
        );
    }}