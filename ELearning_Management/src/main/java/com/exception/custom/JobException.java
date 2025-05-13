package com.exception.custom;

public class JobException extends RuntimeException {
    public JobException(String message) {
        super(message);
    }
}
