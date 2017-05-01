package com.adowsky.controllers;

import com.adowsky.api.CommentRequest;
import com.adowsky.api.ReadingRequest;
import com.adowsky.model.Comment;
import com.adowsky.model.Reading;
import com.adowsky.service.BorrowService;
import com.adowsky.service.CommentService;
import com.adowsky.service.ReadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@AllArgsConstructor
public class BookController {
    private final BorrowService borrowService;
    private final CommentService commentService;
    private final ReadingService readingService;

    @PostMapping(value = "/users/{userId}/borrows/{bookId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity borrow(@PathVariable("bookId") long bookId, @PathVariable("userId") long userId) {
        borrowService.borrow(bookId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/comments", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addComment(@PathVariable long bookId, @RequestBody CommentRequest request) {
        Comment comment = new Comment(request.getUsername(), request.getContent());
        commentService.commentBook(bookId, comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/reading", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity readingBook(@PathVariable long bookId, @RequestBody ReadingRequest request) {
        Date startDate = (request.getProgression() == ReadingRequest.Progression.START) ? request.getDate() : null;
        Date endDate = (request.getProgression() == ReadingRequest.Progression.END) ? request.getDate() : null;
        Reading reading = new Reading(bookId, request.getReaderUsername(), startDate, endDate);
        readingService.addReading(reading);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
