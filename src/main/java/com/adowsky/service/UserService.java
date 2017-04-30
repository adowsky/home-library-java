package com.adowsky.service;


import com.adowsky.model.AuthorizationToken;
import com.adowsky.model.Credentials;
import com.adowsky.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void register(User user) {
        throw new UnsupportedOperationException();
    }

    public AuthorizationToken login(Credentials credentials) {
        throw new UnsupportedOperationException();
    }

    public void confirmRegistration(String confirmationId) {
        throw new UnsupportedOperationException();
    }

}
