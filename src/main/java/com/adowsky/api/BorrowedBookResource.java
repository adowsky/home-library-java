package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BorrowedBookResource {
    private LibraryBookResource book;
    private String ownerUsername;
    private String ownerFirstName;
    private String ownerSurname;
}
