package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookMetadata {
    private final String title;
    private final String author;
    private final String source;
}
