package com.zevseg.web.exception;

import com.zevseg.web.model.ErrorDetail;

import java.util.List;

public class RunTimeException extends Exception {

    public List<ErrorDetail> errorDetails;

    public String reason;

    public RunTimeException() {
    }

    public RunTimeException(String message) {
        super(message);
    }

    public RunTimeException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

}
