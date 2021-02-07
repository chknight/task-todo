package com.chknight.tasklist.entities;

import com.chknight.tasklist.dtos.ToDoItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor
public class ToDoItemEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    private Boolean isCompleted;

    private LocalDateTime createdAt;

    public ToDoItemDTO toDto() {
        return new ToDoItemDTO(
                id,
                text,
                isCompleted,
                createdAt
        );
    }
}
