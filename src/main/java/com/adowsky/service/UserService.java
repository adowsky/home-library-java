package com.adowsky.service;


import com.adowsky.model.*;
import com.adowsky.service.entities.InvitationEntity;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.InternalSecurityException;
import com.adowsky.service.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final AuthorizationService authorizationService;
    private final PermissionService permissionService;
    private final MailService mailService;

    @Transactional
    public void register(User user, String passwordHash, String registrationHash) {
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
                .creationDate(new Timestamp(new Date().getTime()))
                .build();

        log.info("Registering user {} with email={}. Registration hash={}",
                userEntity.getUsername(), userEntity.getEmail(), userEntity.getRegistrationHash());
        userRepository.save(userEntity);
        permissionService.createPermissionForNewUser(userEntity);
        mailService.sendRegistrationMail(user.getEmail(), userEntity.getRegistrationHash());

        if (registrationHash != null) {
            log.info("Registered user is from invitation hash={}", registrationHash);
            Date theDayBefore = valueOfDate(LocalDateTime.now().minusDays(1));
            invitationRepository.findFirstByInvitationHash(registrationHash)
                    .filter(invitation -> theDayBefore.before(invitation.getCreationDate()) && !invitation.isCompleted())
                    .ifPresent(invitation -> {
                        invitation.setCompleted(true);
                        invitationRepository.save(invitation);
                        permissionService.grantPermissionToUser(userEntity, new UserEntity(invitation.getInviterId()));
                    });
        }
    }

    public AuthorizationToken login(Credentials credentials) {
        UserEntity user = userRepository.getByUsername(credentials.getUsername())
                .orElseThrow(UserException::noSuchUser);

        if (!user.getPassword().equals(credentials.getPassword())) {
//            return null;
            throw UserException.invalidCredentials();
        }

        if (!user.isConfirmed()) {
//            return null;
            throw UserException.notConfirmed();
        }

        return authorizationService.generateAuthorizationToken(user.getId());
    }

    public void logout(Long userId) {
        authorizationService.invalidateUserToken(userId);
    }

    public void confirmRegistration(String confirmationId) {
        UserEntity userEntity = userRepository.getByRegistrationHash(confirmationId)
                .orElseThrow(UserException::noSuchUser);

        if (userEntity.isConfirmed()) {
            throw UserException.registrationAlreadyConfirmed();
        }

        if(isOutdated(userEntity)) {
            throw UserException.confirmationOutdated();
        }

        userEntity.setConfirmed(true);
        userRepository.save(userEntity);
    }

    @Transactional
    public void grantPermission(Permission permission) {
        UserEntity owner = userRepository.getByUsername(permission.getResourceOwnerId())
                .orElseThrow(UserException::noSuchUser);
        UserEntity granter = userRepository.getByUsername(permission.getGrantedBy())
                .orElseThrow(UserException::noSuchUser);

        if (!owner.getId().equals(granter.getId())) {
            throw InternalSecurityException.notEnoughRights();
        }

        Optional<UserEntity> grantedTo = userRepository.getByEmail(permission.getGrantedToEmail());

        if (grantedTo.isPresent()) {
            permissionService.grantPermissionToUser(grantedTo.get(), owner);
        } else {
            String invitationHash = UUID.randomUUID().toString();
            InvitationEntity invitationEntity = new InvitationEntity(null, owner.getId(),
                    permission.getGrantedToEmail(), invitationHash, new Date(), false);
            invitationRepository.save(invitationEntity);
            mailService.sendInvitationMail(permission.getGrantedToEmail(), owner.getUsername(), invitationHash);
        }
    }

    public void removeOutdatedNotConfirmedUsers() {
        userRepository.findAllByConfirmed(false).stream()
                .filter(this::isOutdated)
                .forEach(userRepository::delete);
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

    private Date valueOfDate(LocalDateTime localDate) {
        return Date.from(Instant.from(localDate.atZone(ZoneId.systemDefault())));
    }

    private boolean isOutdated(UserEntity userEntity) {
        return userEntity.getCreationDate().toLocalDateTime().plusDays(1).isBefore(LocalDateTime.now());
    }
}
