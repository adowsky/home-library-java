package com.adowsky.service.exception;

public class UserException extends RuntimeException {
    private UserException(String message) {
        super(message);
    }

    public static UserException noSuchUser() {
        return new UserException("No such user");
    }

    public static UserException userExists() {
        return new UserException("User exists");
    }

    public static UserException invalidCredentials() {
        return new UserException("Invalid credentials");
    }

    public static UserException registrationAlreadyConfirmed() {
        return new UserException("Registration already confirmed");
    }

    public static UserException invalidConfirmation() {
        return new UserException("Invalid confirmation hash");
    }
}
