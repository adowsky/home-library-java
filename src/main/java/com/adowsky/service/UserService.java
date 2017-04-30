package com.adowsky.service;


import com.adowsky.model.AuthorizationToken;
import com.adowsky.model.Credentials;
import com.adowsky.model.User;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.UserException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    private final UserEntityMapper userEntityMapper;

    public void register(User user, String passwordHash) {
        boolean exists = userRepository.getByUsername(user.getUserName()).isPresent();
        if (exists) {
            throw UserException.userExists();
        }

        UserEntity userEntity = userEntityMapper.userToUserEntity(user);
        userEntity.setPassword(passwordHash);
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

    public void confirmRegistration(String confirmationId) {
        throw new UnsupportedOperationException();
    }

}
