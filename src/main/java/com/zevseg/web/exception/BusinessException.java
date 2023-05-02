package com.zevseg.web.exception;

import com.zevseg.web.model.ErrorDetail;

import java.util.List;

public class BusinessException extends Exception {

    public List<ErrorDetail> errorDetails;

    public String reason;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

}
