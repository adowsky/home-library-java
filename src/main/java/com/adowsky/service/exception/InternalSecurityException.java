package com.adowsky.service.exception;

public class InternalSecurityException extends InternalException  {
    InternalSecurityException(String message, int code) {
        super(message, code);
    }

    public static InternalSecurityException notEnoughRights() {
        return new InternalSecurityException("Not enough rights", 501);
    }
}
