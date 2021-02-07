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
import org.springframework.transaction.annotation.Transactional;

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
     * Create a new to-do item by the text
     * @param text Text of to-do item
     * @return DTO of created entity
     * @throws ToDoItemValidationException Exception being throw when text is invalid
     */
    public ToDoItemDTO createToDoItem(String text) throws ToDoItemValidationException {

        // Validate Text before proceeding
        validateText(text);

        ToDoItemEntity entity = new ToDoItemEntity();
        entity.setText(text);
        // by default is incompleted when created
        entity.setIsCompleted(false);
        entity.setCreatedAt(LocalDateTime.now());
        return repository.save(entity).toDto();
    }

    /**
     * Find To Do Item by id
     * @param id if of to-do item
     * @return ToDoItemDTO dto of to-do item
     * @throws ToDoItemNotFoundException Throw exception if no item exist in db
     */
    public ToDoItemDTO findToDoItem(Long id) throws ToDoItemNotFoundException {
        return findToDoItemById(id).toDto();
    }

    /**
     * Update to-do item by id
     * @param id if of to-do item
     * @param text text to update
     * @param isCompleted isComplete to update
     * @return updated entity
     * @throws ToDoItemNotFoundException thrown when not found any entity with the id
     * @throws ToDoItemValidationException thrown when text is invalid
     */
    @Transactional
    public ToDoItemDTO updateToDoItemById(Long id, String text, Boolean isCompleted)
            throws ToDoItemNotFoundException, ToDoItemValidationException {

        validateText(text);

        ToDoItemEntity entityToUpdate = findToDoItemById(id);

        entityToUpdate.setText(text);
        entityToUpdate.setIsCompleted(isCompleted);

        return repository.save(entityToUpdate).toDto();
    }

    private void validateText(String text) throws ToDoItemValidationException {
        if (text.length() < 1 || text.length() > 50) {
            throw new ToDoItemValidationException(
                    List.of(
                            ErrorDetailMessage.build("params",
                                    "text", "Must be between 1 and 50 chars long", "")
                    )
            );
        }
    }

    private ToDoItemEntity findToDoItemById(Long id) throws ToDoItemNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ToDoItemNotFoundException(
                List.of(GenericErrorMessage.build(
                        String.format("Item with %d not found", id)
                ))
        ));
    }
}
