package com.chknight.tasklist.controllers.responseentity;

import java.util.List;

public class ToDoItemValidationError {
    public String name;
    public List<ErrorDetailMessage> details;

    public ToDoItemValidationError(String name, List<ErrorDetailMessage> details) {
        this.details = details;
        this.name = name;
    }
}
