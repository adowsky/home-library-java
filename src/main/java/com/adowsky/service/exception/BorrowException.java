package com.adowsky.service.exception;

public class BorrowException extends InternalException {
    private BorrowException(String message, int code) {
        super(message, code);
    }

    public static BorrowException noSuchBook() {
        return new BorrowException("No such book", 101);
    }

    public static BorrowException bookBorrowed() {
        return new BorrowException("Cannot borrow borrowed book", 102);
    }

    public static BorrowException multipleBorrow() {
        return new BorrowException("Book marked as borrowed multiple times", 103);
    }

    public static BorrowException bookNotBorrowed() {
        return new BorrowException("Cannot return not borrowed book", 104);
    }
}
