package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BorrowedBook {
    private LibraryBook libraryBook;
    private UserDetails owner;
}
