package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BorrowHistoryEntry {
    private Long bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private String borrower;

    public BorrowHistoryEntry(Long bookId, Timestamp borrowDate, Timestamp returnDate, String borrower) {
        this.bookId = bookId;
        this.borrowDate = (borrowDate == null) ? null : borrowDate.toLocalDateTime();
        this.returnDate = (returnDate == null) ? null :returnDate.toLocalDateTime();
        this.borrower = borrower;
    }
}
