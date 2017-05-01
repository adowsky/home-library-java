package com.adowsky.controllers;

import com.adowsky.api.CommentRequest;
import com.adowsky.model.Comment;
import com.adowsky.service.BorrowService;
import com.adowsky.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookController {
    private final BorrowService borrowService;
    private final CommentService commentService;

    @PostMapping(value = "/users/{userId}/borrows/{bookId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity borrow(@PathVariable("bookId") long bookId, @PathVariable("userId") long userId) {
        borrowService.borrow(bookId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/comments")
    public ResponseEntity addComment(@PathVariable long bookId, @RequestBody CommentRequest request) {
        Comment comment = new Comment(request.getUsername(), request.getContent());
        commentService.commentBook(bookId, comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
