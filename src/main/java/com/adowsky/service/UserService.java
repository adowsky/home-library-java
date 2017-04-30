package com.adowsky.service;


import com.adowsky.model.AuthorizationToken;
import com.adowsky.model.Credentials;
import com.adowsky.model.User;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.UserExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public void register(User user, String passwordHash) {
        boolean exists = userRepository.getByUsername(user.getUserName()).isPresent();
        if(exists) {
            throw new UserExistsException();
        }

        UserEntity userEntity = userEntityMapper.userToUserEntity(user);
        userEntity.setPassword(passwordHash);
        userRepository.save(userEntity);
    }

    public AuthorizationToken login(Credentials credentials) {
        throw new UnsupportedOperationException();
    }

    public void confirmRegistration(String confirmationId) {
        throw new UnsupportedOperationException();
    }

}
