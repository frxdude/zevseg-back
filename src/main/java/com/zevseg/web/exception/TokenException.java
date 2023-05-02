package com.zevseg.web.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends Exception {

    public String message;

    public HttpStatus status;

    public TokenException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
