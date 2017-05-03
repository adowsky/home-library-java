package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LibraryResponse {
    private List<LibraryBookResource> ownedBooks;
    private List<BorrowedBookResource> borrowedBooks;

}
