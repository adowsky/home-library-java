package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LibraryBookResource {
    private long id;
    private String title;
    private String author;
    private String borrowedBy;
}
