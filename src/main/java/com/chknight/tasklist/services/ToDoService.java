package com.chknight.tasklist.services;

import com.chknight.tasklist.dtos.ToDoItemDTO;
import com.chknight.tasklist.entities.ToDoItemEntity;
import com.chknight.tasklist.exceptions.ToDoItemNotFoundException;
import com.chknight.tasklist.exceptions.ToDoItemValidationException;
import com.chknight.tasklist.repositories.ToDoRepository;
import com.chknight.tasklist.shared.ErrorDetailMessage;
import com.chknight.tasklist.shared.GenericErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository repository;

    @Autowired
    ToDoService(
            ToDoRepository repository
    ) {
        this.repository = repository;
    }

    /**
     * Create a new todo item by the text
     * @param text Text of todo item
     * @return DTO of created entity
     * @throws ToDoItemValidationException Exception being throw when text is invalid
     */
    public ToDoItemDTO createToDoItem(String text) throws ToDoItemValidationException {
        if (text.length() < 1 || text.length() > 50) {
            throw new ToDoItemValidationException(
                    List.of(
                            ErrorDetailMessage.build("params",
                                    "text", "Must be between 1 and 50 chars long", "")
                    )
            );
        }
        ToDoItemEntity entity = new ToDoItemEntity();
        entity.setText(text);
        // by default is incompleted when created
        entity.setIsCompleted(false);
        entity.setCreatedAt(LocalDateTime.now());
        return repository.save(entity).toDto();
    }

    public ToDoItemDTO getToDoItemById(Long id) throws ToDoItemNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ToDoItemNotFoundException(
                List.of(GenericErrorMessage.build(
                        String.format("Item with %d not found", id)
                ))
        )).toDto();
    }
}
