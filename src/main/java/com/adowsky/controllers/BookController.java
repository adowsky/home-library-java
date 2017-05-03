package com.adowsky.controllers;

import com.adowsky.api.CommentRequest;
import com.adowsky.api.ReadingRequest;
import com.adowsky.model.Comment;
import com.adowsky.model.Reading;
import com.adowsky.security.AuthenticationToken;
import com.adowsky.service.BorrowService;
import com.adowsky.service.CommentService;
import com.adowsky.service.ReadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {
    private final BorrowService borrowService;
    private final CommentService commentService;
    private final ReadingService readingService;

    @PostMapping(value = "/books/{bookId}/borrows")
    public ResponseEntity borrow(@PathVariable("bookId") long bookId, AuthenticationToken principal) {
        borrowService.borrow(bookId, principal.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/comments", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addComment(@PathVariable long bookId, @RequestBody CommentRequest request, AuthenticationToken principal) {
        Comment comment = new Comment(principal.getUser().getUsername(), request.getContent());
        commentService.commentBook(bookId, comment, principal.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/reading", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity readingBook(@PathVariable long bookId, @RequestBody ReadingRequest request, AuthenticationToken principal) {
        Date startDate = (request.getProgression() == ReadingRequest.Progression.START) ? request.getDate() : null;
        Date endDate = (request.getProgression() == ReadingRequest.Progression.END) ? request.getDate() : null;
        Reading reading = new Reading(bookId, principal.getUser().getUsername(), startDate, endDate);
        readingService.addReading(reading, principal.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/readings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getReadingBooks(AuthenticationToken principal) {
        List<String> readings = readingService.getUsersReadings(principal.getUser().getId());
        return ResponseEntity.ok(readings);
    }
}
