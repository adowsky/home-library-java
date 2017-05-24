package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Statistics {
    private double average;
    private Book lastBook;
    private List<Book> booksOutsideLibrary;
    private List<BorrowedBook> borrowedBooks;

}
