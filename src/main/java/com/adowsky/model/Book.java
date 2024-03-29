package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Book {
    private final String id;
    private final String title;
    private final String author;
}
