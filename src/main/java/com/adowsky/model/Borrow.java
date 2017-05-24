package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Borrow {
    private Long bookId;
    private String borrower;
}
