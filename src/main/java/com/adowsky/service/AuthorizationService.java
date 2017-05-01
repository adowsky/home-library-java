package com.adowsky.service;

import com.adowsky.model.AuthorizationToken;
import com.adowsky.service.entities.AuthorizationEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
class AuthorizationService {
    private static final int TOKEN_LIFE_DAYS = 5;
    private final AuthorizationRepository authorizationRepository;


    AuthorizationToken generateAuthorizationToken(Long userId) {
        UUID uuid = UUID.randomUUID();
        Date expirationDate = java.sql.Date.valueOf(LocalDate.now().plusDays(TOKEN_LIFE_DAYS));
        AuthorizationEntity authorizationEntity = new AuthorizationEntity(userId, uuid.toString(), expirationDate);
        authorizationRepository.save(authorizationEntity);

        log.info("Generated authorizationToken for user={}", userId);
        return new AuthorizationToken(uuid.toString());
    }
}
