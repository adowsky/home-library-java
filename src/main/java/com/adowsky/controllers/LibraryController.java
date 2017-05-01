package com.adowsky.controllers;

import com.adowsky.api.AddBookRequest;
import com.adowsky.api.BookResource;
import com.adowsky.api.LibraryResponse;
import com.adowsky.model.Book;
import com.adowsky.security.AuthenticationToken;
import com.adowsky.service.LibraryService;
import com.adowsky.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/libraries")
@AllArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;
    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<String>> getLibraries(Principal principal) {
        AuthenticationToken token = (AuthenticationToken)principal;
        List<String> usernames = permissionService.findLibraryOwnerUsernamesGranted(token.getUser().getId());
        return ResponseEntity.ok(usernames);

    }

    @GetMapping("/{username}")
    public ResponseEntity<List<LibraryResponse>> showLibrary(@PathVariable  String username) {
        List<LibraryResponse> books = libraryService.getLibraryOf(username).stream()
                .map(book -> new LibraryResponse(book.getId(), book.getTitle(), book.getAuthor(), book.isBorrowed()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @PostMapping("/{username}")
    public ResponseEntity addBook(@PathVariable String username, @RequestBody AddBookRequest request) {
        Book book = new Book(null, request.getTitle(), request.getAuthor(), false);
        libraryService.addBook(book, username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BookResource>> findBooks(@RequestParam(value = "author", required = false) String author,
                                                        @RequestParam(value = "title", required = false) String title) {
        List<BookResource> books = libraryService.findByTitleAndAuthor(title, author).stream()
                .map(book -> new BookResource(book.getTitle(), book.getAuthor())).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }
}
