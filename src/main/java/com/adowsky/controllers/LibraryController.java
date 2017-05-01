package com.adowsky.controllers;

import com.adowsky.api.AddBookRequest;
import com.adowsky.api.BookResource;
import com.adowsky.api.LibraryResponse;
import com.adowsky.model.Book;
import com.adowsky.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/libraries")
@AllArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<LibraryResponse>> showLibrary(@PathVariable  Long userId) {
        List<LibraryResponse> books = libraryService.getLibraryOf(userId).stream()
                .map(book -> new LibraryResponse(book.getId(), book.getTitle(), book.getAuthor(), book.isBorrowed()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @PostMapping("/{userId}")
    public ResponseEntity addBook(@PathVariable Long userId, @RequestBody AddBookRequest request) {
        Book book = new Book(null, request.getTitle(), request.getAuthor(), false);
        libraryService.addBook(book, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BookResource>> findBooks(@RequestParam("author") String author, @RequestParam("title") String title) {
        List<BookResource> books = libraryService.findByTitleAndAuthor(title, author).stream()
                .map(book -> new BookResource(book.getTitle(), book.getAuthor())).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }
}
