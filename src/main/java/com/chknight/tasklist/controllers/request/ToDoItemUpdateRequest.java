package com.chknight.tasklist.controllers.request;

import lombok.Data;

@Data
public class ToDoItemUpdateRequest {
    private String text;
    private Boolean isCompleted;
}
