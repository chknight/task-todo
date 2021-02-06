package com.chknight.tasklist.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BracketValidationServiceTest {

    private BracketValidationService bracketValidationService;

    @BeforeEach
    public void setup() {
        bracketValidationService = new BracketValidationService();
    }

    @Test
    public void shouldReturnTrueForValidBrackets() {
        Assertions.assertTrue(bracketValidationService.validateBrackets("()"));
        Assertions.assertTrue(bracketValidationService.validateBrackets("[]"));
        Assertions.assertTrue(bracketValidationService.validateBrackets("{}"));

        Assertions.assertTrue(bracketValidationService.validateBrackets("[({})]"));
        Assertions.assertTrue(bracketValidationService.validateBrackets("[({}()[()])]"));
    }

    @Test
    public void shouldReturnTrueForValidBracketsIfHaveOtherCharactersInMiddle(){
        Assertions.assertTrue(bracketValidationService.validateBrackets("[({1})]"));
        Assertions.assertTrue(bracketValidationService.validateBrackets("[ia({}(33)[()])]"));
    }

    @Test
    public void shouldReturnFalseWhenHasUnclosedLeftBrackets() throws Exception {
        Assertions.assertFalse(bracketValidationService.validateBrackets("("));
        Assertions.assertFalse(bracketValidationService.validateBrackets("["));
        Assertions.assertFalse(bracketValidationService.validateBrackets("{"));

        Assertions.assertFalse(bracketValidationService.validateBrackets("{[}]"));
        Assertions.assertFalse(bracketValidationService.validateBrackets("[{)]"));
        Assertions.assertFalse(bracketValidationService.validateBrackets("[{()]"));
    }
}
