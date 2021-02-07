package com.chknight.tasklist.controllers.responseentity;

import java.util.Map;

public class ToDoItemValidationError {
    public Map<String, String> details;
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
