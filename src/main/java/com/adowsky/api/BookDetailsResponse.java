package com.adowsky.api;

import com.adowsky.model.BorrowHistoryEntry;
import com.adowsky.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BookDetailsResponse {
    private final String title;
    private final String author;
    private final List<Comment> comments;
    private final List<BorrowHistoryResource> borrows;
}
