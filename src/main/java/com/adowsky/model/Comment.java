package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Comment {
    private Long bookId;
    private String authorUsername;
    private String comment;
}
