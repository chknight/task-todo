package com.chknight.tasklist.shared;

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

    static public ErrorDetailMessage build(String location, String param, String msg, String value) {
        return new ErrorDetailMessage(location, param, msg, value);
    }

    public ErrorDetailMessage(String location, String param, String msg, String value) {
        this.location = location;
        this.param = param;
        this.msg = msg;
        this.value = value;
    }
}
