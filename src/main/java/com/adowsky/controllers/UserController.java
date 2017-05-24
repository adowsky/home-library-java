package com.adowsky.controllers;

import com.adowsky.api.RegistrationRequest;
import com.adowsky.model.Book;
import com.adowsky.model.Library;
import com.adowsky.model.Statistics;
import com.adowsky.model.User;
import com.adowsky.security.AuthenticationToken;
import com.adowsky.service.LibraryService;
import com.adowsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final LibraryService libraryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegistrationRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getFirstName(), request.getLastName());
        userService.register(user, request.getPasswordHash(), request.getRegistrationHash());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/confirmation")
    public ResponseEntity confirmRegistration(@RequestParam("confirm") String confirmationId) {
        userService.confirmRegistration(confirmationId);
        return ResponseEntity.noContent().build();
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Statistics> getStatistics(AuthenticationToken token) {
        Statistics statistics = libraryService.getStatisticsForUser(token.getUser().getId());
        return ResponseEntity.ok(statistics);
    }
}
