package com.chknight.tasklist.controllers.responseentity;

import lombok.Getter;
import lombok.Setter;

/**
 * Class to describe details of error message
 */
@Getter @Setter
public class ErrorDetailMessage {
    String location;
    String param;
    String msg;
    String value;

    public ErrorDetailMessage(String location, String param, String msg, String value) {
        this.location = location;
        this.param = param;
        this.msg = msg;
        this.value = value;
    }
}
