package com.zevseg.web.contant;


public enum ExceptionType {

    VALIDATION("Validation"),

    NOT_FOUND("Not found"),

    BUSINESS("Business"),

    RUN_TIME("Run time"),

    RMI("RMI"),

    ACCESS_DENIED("Access denied"),

    UNAUTHORIZED("Unauthorized"),

    TOKEN("Token"),

    FATAL("Fatal");

    private final String value;

    ExceptionType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
