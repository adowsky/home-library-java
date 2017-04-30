package com.adowsky.service;


import com.adowsky.model.AuthorizationToken;
import com.adowsky.model.Credentials;
import com.adowsky.model.User;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.UserException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public void register(User user, String passwordHash) {
        boolean exists = userRepository.getByUsername(user.getUsername()).isPresent();
        if (exists) {
            throw UserException.userExists();
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(user.getFirstName())
                .password(passwordHash)
                .confirmed(false)
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .registrationHash(UUID.randomUUID().toString())
                .build();
        userRepository.save(userEntity);
    }

    public AuthorizationToken login(Credentials credentials) {
        UserEntity user = userRepository.getByUsername(credentials.getUsername())
                .orElseThrow(UserException::noSuchUser);

        if (!user.getPassword().equals(credentials.getPassword())) {
            throw UserException.invalidCredentials();
        }

        return authorizationService.generateAuthorizationToken(user.getId());
    }

    public void confirmRegistration(String username, String confirmationId) {
        UserEntity userEntity = userRepository.getByUsername(username)
                .orElseThrow(UserException::noSuchUser);

        if(userEntity.isConfirmed()) {
            throw UserException.registrationAlreadyConfirmed();
        }

        if(!userEntity.getRegistrationHash().equals(confirmationId)) {
            throw UserException.invalidConfirmation();
        }

        userEntity.setConfirmed(true);
        userRepository.save(userEntity);
    }

}
