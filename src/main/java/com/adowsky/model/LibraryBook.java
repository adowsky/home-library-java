package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LibraryBook {
    private Long id;
    private String title;
    private String author;
    private String borrowedBy;
}
