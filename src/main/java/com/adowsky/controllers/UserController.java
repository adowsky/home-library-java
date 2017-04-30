package com.adowsky.controllers;

import com.adowsky.api.LoginRequest;
import com.adowsky.api.LoginResponse;
import com.adowsky.api.RegistrationRequest;
import com.adowsky.model.AuthorizationToken;
import com.adowsky.model.Credentials;
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
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegistrationRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getFirstName(), request.getLastName());
        userService.register(user, request.getPasswordHash());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity login(@PathVariable String username, @Valid @RequestBody LoginRequest request) {
        Credentials credentials = new Credentials(username, request.getPasswordHash());
        AuthorizationToken authorizationToken = userService.login(credentials);
        return ResponseEntity.ok(new LoginResponse(authorizationToken.getToken()));
    }

    @GetMapping(value = "/{username}/confirmation")
    public ResponseEntity confirmRegistration(@RequestParam("confirm") String confirmationId) {
        throw new UnsupportedOperationException();
    }
}
