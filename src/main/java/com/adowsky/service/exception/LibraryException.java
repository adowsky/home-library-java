package com.adowsky.service.exception;

public class LibraryException extends InternalException {
    private LibraryException(String message, int code) {
        super(message, code);
    }

    public static LibraryException cannotAddBorrowed() {
        return new LibraryException("Cannot add borrowed book", 401);
    }

    public static LibraryException search() {
        return new LibraryException("Cannot find without data", 402);
    }
    public static LibraryException noSuchBook() {
        return new LibraryException("Cannot find given book", 403);
    }
    public static LibraryException bookExists() {
        return new LibraryException("Given book already exists", 404);
    }
}
