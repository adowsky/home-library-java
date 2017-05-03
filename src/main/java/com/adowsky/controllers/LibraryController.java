package com.adowsky.controllers;

import com.adowsky.api.*;
import com.adowsky.model.Book;
import com.adowsky.model.Library;
import com.adowsky.model.Permission;
import com.adowsky.security.AuthenticationToken;
import com.adowsky.service.LibraryService;
import com.adowsky.service.PermissionService;
import com.adowsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/libraries")
@AllArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;
    private final PermissionService permissionService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<String>> getLibraries(AuthenticationToken principal) {
        List<String> usernames = permissionService.findLibraryOwnerUsernamesGranted(principal.getUser().getId());
        return ResponseEntity.ok(usernames);

    }

    @GetMapping("/{username}")
    @PostAuthorize("hasPermission(#username, 'read')")
    public ResponseEntity<LibraryResponse> showLibrary(@PathVariable String username) {
        Library library = libraryService.getLibraryOf(username);
        List<LibraryBookResource> books = library.getOwnedBooks().stream()
                .map(book -> new LibraryBookResource(book.getId(), book.getTitle(), book.getAuthor(), book.getBorrowedBy()))
                .collect(Collectors.toList());
        List<BorrowedBookResource> borrowedBooks = library.getBorrowedBooks().stream()
                .map(book -> new BorrowedBookResource(new LibraryBookResource(book.getBook().getId(), book.getBook().getTitle(), book.getBook().getAuthor(), book.getBook().getBorrowedBy()),
                        book.getOwner().getUsername(), book.getOwner().getFirstName(), book.getOwner().getSurname()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LibraryResponse(books, borrowedBooks));
    }

    @PostMapping("/{username}")
    @PostAuthorize("hasPermission(#username, 'read')")
    public ResponseEntity<Book> addBook(@PathVariable String username, @RequestBody AddBookRequest request) {
        Book book = new Book(null, request.getTitle(), request.getAuthor(), null);
        Book created = libraryService.addBook(book, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BookResource>> findBooks(@RequestParam(value = "author", required = false) String author,
                                                        @RequestParam(value = "title", required = false) String title) {
        List<BookResource> books = libraryService.findByTitleAndAuthor(title, author).stream()
                .map(book -> new BookResource(book.getTitle(), book.getAuthor())).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @PostMapping("/{username}/permissions")
    @PostAuthorize("hasPermission(#username, 'grant')")
    public ResponseEntity grantPermission(@RequestBody GrantPermissionRequest request, AuthenticationToken principal, @RequestParam String username) {
        Permission permission = new Permission(request.getGrantedUsername(), username, principal.getUser().getUsername());
        userService.grantPermission(permission);
        return ResponseEntity.noContent().build();
    }
}
