package com.adowsky.service;

import com.adowsky.model.SimpleUser;
import com.adowsky.service.entities.AuthorizationEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationVerificationService {
    private final AuthorizationRepository authorizationRepository;
    private final UserService userService;

    public SimpleUser getUserByToken(String authToken) {
        AuthorizationEntity entity = authorizationRepository.getByToken(authToken);
        if(entity == null) {
            return null;
        }
        return userService.getUserById(entity.getId());

    }
}
