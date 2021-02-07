package com.chknight.tasklist.exceptions;

import com.chknight.tasklist.shared.GenericErrorMessage;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ToDoItemNotFoundException extends Exception {
    public List<GenericErrorMessage> errors;
}
