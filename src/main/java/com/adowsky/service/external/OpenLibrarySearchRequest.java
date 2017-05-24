package com.adowsky.service.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OpenLibrarySearchRequest {
    private String author;
    private String title;
}
