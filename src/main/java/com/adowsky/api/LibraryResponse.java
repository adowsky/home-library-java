package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LibraryResponse {
    private long id;
    private String title;
    private String author;
    private boolean borrowed;
}
