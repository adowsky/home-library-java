package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Library {
    private final List<Book> ownedBooks;
    private final List<BorrowedBook> borrowedBooks;
}
