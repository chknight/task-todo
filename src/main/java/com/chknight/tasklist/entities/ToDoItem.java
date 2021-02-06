package com.chknight.tasklist.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ToDoItem {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    private Boolean isCompleted;

    private LocalDateTime createdAt;
}
