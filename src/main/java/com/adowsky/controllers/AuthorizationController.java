package com.adowsky.controllers;

import com.adowsky.api.LoginRequest;
import com.adowsky.api.LoginResponse;
import com.adowsky.model.AuthorizationToken;
import com.adowsky.model.Credentials;
import com.adowsky.security.AuthenticationToken;
import com.adowsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/authorize")
public class AuthorizationController {
    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity login(@RequestHeader("username") String username, @Valid @RequestBody LoginRequest request) {
        Credentials credentials = new Credentials(username, request.getPasswordHash());
        AuthorizationToken authorizationToken = userService.login(credentials);
        return ResponseEntity.ok(new LoginResponse(authorizationToken.getToken(), authorizationToken.getExpirationDate()));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity logout(AuthenticationToken token) {
        userService.logout(token.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}
