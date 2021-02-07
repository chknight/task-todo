package com.chknight.tasklist.controllers;

import com.chknight.tasklist.services.BracketValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = { TaskController.class })
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BracketValidationService bracketValidationService;

    @Test
    public void shouldReturnBalancedTrueWithValidInput() throws Exception {
        String testString = "({})";
        when(bracketValidationService.validateBrackets(testString)).thenReturn(true);

        mockMvc.perform(
                get("/tasks/validateBrackets")
                        .param("input", testString)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.isBalanced", is(true))
        ).andExpect(
                jsonPath("$.input", is(testString))
        );
    }

    @Test
    public void shouldReturnBalancedFalseWithInvalidInput() throws Exception {
        String testString = "((})";
        when(bracketValidationService.validateBrackets(testString)).thenReturn(false);

        mockMvc.perform(
                get("/tasks/validateBrackets")
                        .param("input", testString)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.isBalanced", is(false))
        ).andExpect(
                jsonPath("$.input", is(testString))
        );
    }

    @Test
    public void shouldReturn400IfInputIsTooShort() throws Exception {
        String testString = "";
        mockMvc.perform(
                get("/tasks/validateBrackets")
                        .param("input", testString)
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
    public void shouldReturn400IfInputIsTooLong() throws Exception {
        String testString = "a".repeat(51);
        mockMvc.perform(
                get("/tasks/validateBrackets")
                        .param("input", testString)
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
}