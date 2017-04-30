package com.adowsky.service.exception;

public class LibraryException extends RuntimeException {
    private LibraryException(String message) {
        super(message);
    }

    public static LibraryException cannotAddBorrowed() {
        return new LibraryException("Cannot add borrowed book");
    }
}
