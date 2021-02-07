package com.chknight.tasklist.controllers;

import com.chknight.tasklist.controllers.response.BalanceTestResult;
import com.chknight.tasklist.shared.ErrorDetailMessage;
import com.chknight.tasklist.controllers.response.ToDoItemValidationError;
import com.chknight.tasklist.services.BracketValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tags(value = {@Tag(name = "task")})
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
    @Operation(
            summary = "Checks if brackets in a string are balanced",
            tags = {"task"},
            description = "Brackets in a string are considered to be balanced if the following criteria are met:\n" +
                    "        - For every opening bracket (i.e., **`(`**, **`{`**, or **`[`**), there is a matching closing bracket (i.e., **`)`**, **`}`**, or **`]`**) of the same type (i.e., **`(`** matches **`)`**, **`{`** matches **`}`**, and **`[`** matches **`]`**). An opening bracket must appear before (to the left of) its matching closing bracket. For example, **`]{}[`** is not balanced.\n" +
                    "        - No unmatched braces lie between some pair of matched bracket. For example, **`({[]})`** is balanced, but **`{[}]`** and **`[{)]`** are not balanced.",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BalanceTestResult.class,
                                    example = "{\n" +
                                            "    \"input\": \"[{()}]\",\n" +
                                            "    \"isBalanced\": true\n" +
                                            "}"))),
                    @ApiResponse(responseCode = "400", description = "Pet not found")
            }
    )
    public ResponseEntity<?> validateBrackets(
            @RequestParam(value = "input") String input
    ) {
        int minInputLength = 1;
        int maxInputLength = 50;
        if (input.length() < minInputLength || input.length() > maxInputLength) {
            return ResponseEntity.badRequest()
                    .body(
                            new ToDoItemValidationError(
                                    "ValidationError",
                                    List.of(
                                            new ErrorDetailMessage("params", "text", "Must be between 1 and 50 chars long", "")
                                    )
                            )
                    );
        }
        return ResponseEntity.ok(
                new BalanceTestResult(
                        input,
                        bracketValidationService.validateBrackets(input)
                )
        );
    }
}
