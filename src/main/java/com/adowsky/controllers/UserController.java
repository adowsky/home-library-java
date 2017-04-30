package com.adowsky.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity register() {
        throw new UnsupportedOperationException();
    }

    @PostMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity login(@PathVariable String username) {
        throw new UnsupportedOperationException();
    }

    @GetMapping(value = "/{username}/confirmation")
    public ResponseEntity confirmRegistration(@RequestParam("confirm") String confirmationId) {
        throw new UnsupportedOperationException();
    }
}
