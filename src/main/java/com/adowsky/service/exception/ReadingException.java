package com.adowsky.service.exception;

public class ReadingException extends InternalException {
    private ReadingException(String message, int code) {
        super(message, code);
    }

    public static ReadingException noStartDate() {
        return new ReadingException("Cannot end reading without start", 301);
    }
}
