package com.chknight.tasklist.exceptions;

import com.chknight.tasklist.shared.ErrorDetailMessage;
import java.util.List;

public class ToDoItemValidationException extends Exception {
    public List<ErrorDetailMessage> errors;

    public ToDoItemValidationException(List<ErrorDetailMessage> errors) {
        this.errors = errors;
    }
}
