package com.chknight.tasklist.controllers;

import com.chknight.tasklist.controllers.responseentity.BalanceTestResult;
import com.chknight.tasklist.services.BracketValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final BracketValidationService bracketValidationService;

    @Autowired
    public TaskController(
            BracketValidationService bracketValidationService
    ) {
        this.bracketValidationService = bracketValidationService;
    }

    /**
     * Get endpoint to validate whether brackets in string are balanced
     * @param input string to validated
     * @return Result of validation
     */
    @GetMapping(path = "/validateBrackets")
    public ResponseEntity<BalanceTestResult> validateBrackets(
            @RequestParam(value = "input") String input
    ) {
        int minInputLength = 1;
        int maxInputLength = 50;
        if (input.length() < minInputLength || input.length() > maxInputLength) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(
                new BalanceTestResult(
                        input,
                        bracketValidationService.validateBrackets(input)
                )
        );
    }
}
