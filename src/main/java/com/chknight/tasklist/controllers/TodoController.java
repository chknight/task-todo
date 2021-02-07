package com.chknight.tasklist.controllers;

import com.chknight.tasklist.controllers.request.ToDoItemAddRequest;
import com.chknight.tasklist.controllers.request.ToDoItemUpdateRequest;
import com.chknight.tasklist.controllers.response.ToDoItemNotFoundError;
import com.chknight.tasklist.controllers.response.ToDoItemValidationError;
import com.chknight.tasklist.dtos.ToDoItemDTO;
import com.chknight.tasklist.exceptions.ToDoItemNotFoundException;
import com.chknight.tasklist.exceptions.ToDoItemValidationException;
import com.chknight.tasklist.services.ToDoService;
import com.chknight.tasklist.shared.ErrorDetailMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
@Tags(value = {@Tag(name = "todo")})
public class TodoController {

    private final ToDoService toDoService;

    @Autowired
    public TodoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping(
            value = "",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )

    public ResponseEntity<?> createToDoItem(
            @Valid @RequestBody ToDoItemAddRequest request,
            Errors errors
    ) throws ToDoItemValidationException {
        if (errors.hasErrors()) {
            return buildValidationError(errors);
        }
        return ResponseEntity.ok(
                toDoService.createToDoItem(request.getText())
        );
    }

    @GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> findToDoItemById(
            @PathVariable("id") Long id
    ) throws ToDoItemNotFoundException {
        return ResponseEntity.ok(
                toDoService.findToDoItem(id)
        );
    }

    @PatchMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> updateToDoItemById(
            @PathVariable("id") Long id,
            @Valid @RequestBody ToDoItemUpdateRequest request,
            Errors errors
    ) throws ToDoItemNotFoundException, ToDoItemValidationException {
        if (errors.hasErrors()) {
            return buildValidationError(errors);
        }
        return ResponseEntity.ok(
                toDoService.updateToDoItemById(id, request.getText(), request.getIsCompleted())
        );
    }

    @ExceptionHandler(ToDoItemValidationException.class)
    public ResponseEntity<ToDoItemValidationError> handle(ToDoItemValidationException exception) {
        return ResponseEntity.badRequest().body(
                new ToDoItemValidationError(
                        "ValidationError",
                        exception.errors
                )
        );
    }

    @ExceptionHandler(ToDoItemNotFoundException.class)
    public ResponseEntity<ToDoItemNotFoundError> handle(ToDoItemNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ToDoItemNotFoundError(
                                "NotFoundError",
                                exception.errors
                        )
                );
    }

    public ResponseEntity<?> buildValidationError(Errors errors) {
        List<ErrorDetailMessage> validationErrors = errors.getFieldErrors().stream().map((error) -> ErrorDetailMessage.build(
                "params",
                error.getField(),
                error.getDefaultMessage(),
                error.getRejectedValue() == null ? "null" : error.getRejectedValue().toString()
        )).collect(Collectors.toList());

        return ResponseEntity
                .badRequest()
                .body(
                        new ToDoItemValidationError(
                                "ValidationError",
                                validationErrors
                        )
                );
    }
}
