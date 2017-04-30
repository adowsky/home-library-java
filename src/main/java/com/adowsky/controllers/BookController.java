package com.adowsky.controllers;

import com.adowsky.service.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
public class BookController {
    private final BorrowService borrowService;

    @PostMapping(value = "/users/{userId}/borrows/{bookId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity borrow(@PathVariable("bookId") @Valid @NotNull Long bookId,
                                 @PathVariable("userId") @Valid @NotNull Long userId) {
        borrowService.borrow(bookId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
