package com.chknight.tasklist.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ToDoItemDTO {
    private Long id;

    private String text;

    private Boolean isCompleted;

    private LocalDateTime createdAt;
}
