package com.adowsky.service.exception;

public class BorrowException extends RuntimeException {
    private BorrowException(String message) {
        super(message);
    }

    public static BorrowException noSuchBook() {
        return new BorrowException("No such book");
    }

    public static BorrowException bookBorrowed() {
        return new BorrowException("Cannot borrow borrowed book");
    }
}
