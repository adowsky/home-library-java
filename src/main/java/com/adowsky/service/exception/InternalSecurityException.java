package com.adowsky.service.exception;

public class InternalSecurityException extends RuntimeException  {
    InternalSecurityException(String message) {
        super(message);
    }

    public static InternalSecurityException notEnoughRights() {
        return new InternalSecurityException("Not enough rights");
    }
}
