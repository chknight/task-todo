package com.chknight.tasklist.services;

import com.chknight.tasklist.dtos.ToDoItemDTO;
import com.chknight.tasklist.entities.ToDoItemEntity;
import com.chknight.tasklist.exceptions.ToDoItemNotFoundException;
import com.chknight.tasklist.exceptions.ToDoItemValidationException;
import com.chknight.tasklist.repositories.ToDoRepository;
import com.chknight.tasklist.shared.GenericErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    private ToDoRepository repository;

    private ToDoService toDoService;

    @BeforeEach
    public void setup() {
        repository = mock(ToDoRepository.class);
        toDoService = new ToDoService(repository);
    }

    @Test
    public void shouldReturnToDoItemAfterSavingCorrectly() throws Exception {
        ToDoItemEntity entityToSave = new ToDoItemEntity();
        String text = "test text";

        when(repository.save(any(ToDoItemEntity.class))).thenReturn(entityToSave);

        ToDoItemDTO result = toDoService.createToDoItem(text);
        verify(repository, times(1)).save(argThat(
                (toDoItemEntity -> toDoItemEntity.getText().equals(text))
        ));
    }

    @Test
    public void shouldThrowValidationErrorIfTextIsTooShort() {
        String text = "";

        ToDoItemValidationException exception = Assertions.assertThrows(
                ToDoItemValidationException.class,
                () -> toDoService.createToDoItem(text)
        );
        Assertions.assertEquals(1, exception.errors.size());
        Assertions.assertEquals("params", exception.errors.get(0).getLocation());
        Assertions.assertEquals("text", exception.errors.get(0).getParam());
        Assertions.assertEquals("Must be between 1 and 50 chars long", exception.errors.get(0).getMsg());
        Assertions.assertEquals("", exception.errors.get(0).getValue());
    }

    @Test
    public void shouldReturnToDoItemDTOWhenFoundDataById() throws Exception {
        ToDoItemEntity entityFound = new ToDoItemEntity();
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(entityFound));
        ToDoItemDTO result = toDoService.getToDoItemById(id);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void shouldThrowNotFoundExceptionIfCouldNotFoundById() {
        ToDoItemEntity entityFound = new ToDoItemEntity();
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        ToDoItemNotFoundException exception = Assertions.assertThrows(
                ToDoItemNotFoundException.class,
                () -> toDoService.getToDoItemById(id)
        );
        Assertions.assertEquals(1, exception.errors.size());
        Assertions.assertEquals(GenericErrorMessage.build("Item with 1 not found"),exception.errors.get(0));
    }
}
