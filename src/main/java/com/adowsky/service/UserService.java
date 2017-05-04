package com.adowsky.service;


import com.adowsky.model.*;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.InternalSecurityException;
import com.adowsky.service.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final PermissionService permissionService;

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

        log.info("Registering user {} with email={}. Registration hash={}",
                userEntity.getUsername(), userEntity.getEmail(), userEntity.getRegistrationHash());
        userRepository.save(userEntity);
        permissionService.createPermissionForNewUser(userEntity);
    }

    public AuthorizationToken login(Credentials credentials) {
        UserEntity user = userRepository.getByUsername(credentials.getUsername())
                .orElseThrow(UserException::noSuchUser);

        if (!user.getPassword().equals(credentials.getPassword())) {
//            return null;
            throw UserException.invalidCredentials();
        }

        return authorizationService.generateAuthorizationToken(user.getId());
    }

    public void logout(Long userId) {
        authorizationService.invalidateUserToken(userId);
    }

    public void confirmRegistration(String username, String confirmationId) {
        UserEntity userEntity = userRepository.getByUsername(username)
                .orElseThrow(UserException::noSuchUser);

        if (userEntity.isConfirmed()) {
            throw UserException.registrationAlreadyConfirmed();
        }

        if (!userEntity.getRegistrationHash().equals(confirmationId)) {
            throw UserException.invalidConfirmation();
        }

        userEntity.setConfirmed(true);
        userRepository.save(userEntity);
    }

    public void grantPermission(Permission permission) {
        UserEntity owner = userRepository.getByUsername(permission.getResourceOwnerId())
                .orElseThrow(UserException::noSuchUser);
        UserEntity granter = userRepository.getByUsername(permission.getGrantedBy())
                .orElseThrow(UserException::noSuchUser);
        UserEntity grantedTo = userRepository.getByUsername(permission.getGrantedTo())
                .orElseThrow(UserException::noSuchUser);

        if (!owner.getId().equals(granter.getId())) {
            throw InternalSecurityException.notEnoughRights();
        }

        permissionService.grantPermissionToUser(grantedTo, owner);
    }

    long getUserId(String username) {
        UserEntity user = userRepository.getByUsername(username)
                .orElseThrow(UserException::noSuchUser);

        return user.getId();
    }

    SimpleUser getUserById(long id) {
        UserEntity entity = userRepository.findOne(id);
        return new SimpleUser(entity.getId(), entity.getUsername());
    }

    User getRichUserById(long id) {
        UserEntity entity = userRepository.findOne(id);
        return new User(entity.getUsername(), entity.getEmail(), entity.getFirstName(), entity.getSurname());
    }
}
