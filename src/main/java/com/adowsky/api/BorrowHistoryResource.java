package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BorrowHistoryResource {
    private Long bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private String borrower;
}
