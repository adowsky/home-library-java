package com.adowsky.controllers;

import com.adowsky.api.*;
import com.adowsky.model.Book;
import com.adowsky.model.BorrowHistoryEntry;
import com.adowsky.model.Comment;
import com.adowsky.model.Reading;
import com.adowsky.security.AuthenticationToken;
import com.adowsky.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class BookController {
    private final BorrowService borrowService;
    private final CommentService commentService;
    private final ReadingService readingService;
    private final LibraryService libraryService;
    private final UserService userService;

    @PostMapping(value = "/books/{bookId}/borrows")
    public ResponseEntity borrow(@PathVariable("bookId") long bookId, AuthenticationToken principal, @RequestBody BorrowRequest request) {
        Long borrowTo = (request.isOutside()) ? null : principal.getUser().getId();
        if (request.getType() == BorrowRequest.Type.BORROW)
            borrowService.borrow(bookId, borrowTo);
        else
            borrowService.returnBook(bookId, borrowTo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/books/{bookId}/comments", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addComment(@PathVariable long bookId, @RequestBody CommentRequest request, AuthenticationToken principal) {
        Comment comment = new Comment(bookId, principal.getUser().getUsername(), request.getContent());
        commentService.commentBook(comment);
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
    public ResponseEntity<List<Book>> getReadingBooks(AuthenticationToken principal) {
        List<Book> readings = readingService.getUsersReadings(principal.getUser().getId());
        return ResponseEntity.ok(readings);
    }

    @GetMapping(value = "/books/{bookId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookDetailsResponse> getBookDetails(@PathVariable Long bookId) {
        Book book = libraryService.getBookDetails(bookId);
        List<Comment> comments = commentService.getCommentsOfBook(bookId);
        List<BorrowHistoryResource> borrowHistory = borrowService.getBookBorrowHistory(bookId).stream()
                .map(entry -> new BorrowHistoryResource(entry.getBookId(), entry.getBorrowDate(),
                        entry.getReturnDate(), entry.getBorrower()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new BookDetailsResponse(book.getTitle(), book.getAuthor(), comments, borrowHistory));
    }

}
