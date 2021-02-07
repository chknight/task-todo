package com.chknight.tasklist.services;

import com.chknight.tasklist.dtos.ToDoItemDTO;
import com.chknight.tasklist.entities.ToDoItemEntity;
import com.chknight.tasklist.repositories.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void shouldReturnToDoItemDTOWhenFoundDataById() throws Exception {
        ToDoItemEntity entityFound = new ToDoItemEntity();
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(entityFound));
        ToDoItemDTO result = toDoService.getToDoItemById(id);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void shouldThrowNotFoundExceptionIfCouldNotFoundById() throws Exception {
        ToDoItemEntity entityFound = new ToDoItemEntity();
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(entityFound));
        ToDoItemDTO result = toDoService.getToDoItemById(id);
        verify(repository, times(1)).findById(id);
    }
}
