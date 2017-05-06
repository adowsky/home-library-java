package com.adowsky.service.exception;

import lombok.Getter;

@Getter
public class InternalException extends RuntimeException {
    private int errorCode;

    public InternalException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
