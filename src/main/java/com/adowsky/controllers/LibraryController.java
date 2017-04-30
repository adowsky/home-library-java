package com.adowsky.controllers;

import com.adowsky.api.LibraryResponse;
import com.adowsky.model.Book;
import com.adowsky.service.LibraryService;
import lombok.AllArgsConstructor;
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
    
}
