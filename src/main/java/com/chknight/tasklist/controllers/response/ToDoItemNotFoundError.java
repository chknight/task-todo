package com.chknight.tasklist.controllers.response;

import com.chknight.tasklist.shared.GenericErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ToDoItemNotFoundError {
    public String name;
    public List<GenericErrorMessage> details;

}
