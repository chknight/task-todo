package com.chknight.tasklist.controllers;

import com.chknight.tasklist.controllers.request.ToDoItemAddRequest;
import com.chknight.tasklist.controllers.response.ToDoItemNotFoundError;
import com.chknight.tasklist.controllers.response.ToDoItemValidationError;
import com.chknight.tasklist.dtos.ToDoItemDTO;
import com.chknight.tasklist.exceptions.ToDoItemNotFoundException;
import com.chknight.tasklist.exceptions.ToDoItemValidationException;
import com.chknight.tasklist.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private ToDoService toDoService;

    @Autowired
    public TodoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/")
    public ResponseEntity<ToDoItemDTO> createToDoItem(
            @RequestBody ToDoItemAddRequest request
            ) throws ToDoItemValidationException {
        return ResponseEntity.ok(
                toDoService.createToDoItem(request.getText())
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createToDoItem(
            @PathVariable("id") Long id
    ) throws ToDoItemNotFoundException {
        return ResponseEntity.ok(
                toDoService.getToDoItemById(id)
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
}
