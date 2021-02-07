package com.chknight.tasklist.controllers.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ToDoItemUpdateRequest {
    @NotNull
    private String text;
    @NotNull
    private Boolean isCompleted;
}
