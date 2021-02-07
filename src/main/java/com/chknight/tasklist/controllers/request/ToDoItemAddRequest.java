package com.chknight.tasklist.controllers.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ToDoItemAddRequest {
    @NotNull
    private String text;
}
