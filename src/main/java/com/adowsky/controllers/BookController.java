package com.adowsky.controllers;

import com.adowsky.api.CommentRequest;
import com.adowsky.api.ReadingRequest;
import com.adowsky.model.Comment;
import com.adowsky.model.Reading;
import com.adowsky.model.SimpleUser;
import com.adowsky.security.AuthenticationToken;
import com.adowsky.service.BorrowService;
import com.adowsky.service.CommentService;
import com.adowsky.service.ReadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@RestController
@AllArgsConstructor
public class BookController {
    private final BorrowService borrowService;
    private final CommentService commentService;
    private final ReadingService readingService;

    @PostMapping(value = "/books/{bookId}/borrows")
    public ResponseEntity borrow(@PathVariable("bookId") long bookId, Principal principal) {
        AuthenticationToken token = (AuthenticationToken) principal;
        borrowService.borrow(bookId, token.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/comments", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addComment(@PathVariable long bookId, @RequestBody CommentRequest request, Principal principal) {
        AuthenticationToken token = (AuthenticationToken) principal;
        Comment comment = new Comment(token.getUser().getUsername(), request.getContent());
        commentService.commentBook(bookId, comment, token.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/reading", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity readingBook(@PathVariable long bookId, @RequestBody ReadingRequest request, Principal principal) {
        AuthenticationToken token = (AuthenticationToken) principal;
        Date startDate = (request.getProgression() == ReadingRequest.Progression.START) ? request.getDate() : null;
        Date endDate = (request.getProgression() == ReadingRequest.Progression.END) ? request.getDate() : null;
        Reading reading = new Reading(bookId, token.getUser().getUsername(), startDate, endDate);
        readingService.addReading(reading, token.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
