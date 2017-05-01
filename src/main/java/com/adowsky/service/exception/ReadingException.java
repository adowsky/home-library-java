package com.adowsky.service.exception;

public class ReadingException extends RuntimeException {
    private ReadingException(String message) {
        super(message);
    }

    public static ReadingException noStartDate() {
        return new ReadingException("Cannot end reading without start");
    }
}
