package com.adowsky.service.exception;

import com.adowsky.service.entities.UserEntity;

public class UserException extends InternalException {
    private UserException(String message, int code) {
        super(message, code);
    }

    public static UserException noSuchUser() {
        return new UserException("No such user", 201);
    }

    public static UserException userExists() {
        return new UserException("User exists", 202);
    }

    public static UserException invalidCredentials() {
        return new UserException("Invalid credentials", 203);
    }

    public static UserException registrationAlreadyConfirmed() {
        return new UserException("Registration already confirmed", 204);
    }

    public static UserException confirmationOutdated() {
        return new UserException("Registration confirmation outdated", 207);
    }

    public static UserException notConfirmed() {
        return new UserException("User need to be confirmed to login", 206);
    }

    public static UserException invalidConfirmation() {
        return new UserException("Invalid confirmation hash", 205);
    }
}
