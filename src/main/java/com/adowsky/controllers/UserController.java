package com.adowsky.controllers;

import com.adowsky.api.RegistrationRequest;
import com.adowsky.model.User;
import com.adowsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegistrationRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getFirstName(), request.getLastName());
        userService.register(user, request.getPasswordHash());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{username}/confirmation")
    public ResponseEntity confirmRegistration(@PathVariable String username, @RequestParam("confirm") String confirmationId) {
        userService.confirmRegistration(username, confirmationId);
        return ResponseEntity.noContent().build();
    }
}
