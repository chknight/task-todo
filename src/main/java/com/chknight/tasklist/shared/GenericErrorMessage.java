package com.chknight.tasklist.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericErrorMessage {
    private String message;

    static public GenericErrorMessage build(String message) {
        return new GenericErrorMessage(message);
    }
}
