package com.zevseg.web.exception;

import com.zevseg.web.model.ErrorDetail;

import java.util.List;

public class RMIException extends Exception {

    public List<ErrorDetail> errorDetails;

    public String reason;

    public RMIException() {
    }

    public RMIException(String message) {
        super(message);
    }

    public RMIException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

}
