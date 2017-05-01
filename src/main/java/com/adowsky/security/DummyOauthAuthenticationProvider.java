package com.adowsky.security;

import com.adowsky.model.SimpleUser;
import com.adowsky.service.AuthorizationVerificationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DummyOauthAuthenticationProvider implements AuthenticationProvider {
    private final AuthorizationVerificationService authorizationService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String token = ((AuthenticationToken) authentication).getToken();
        SimpleUser user = authorizationService.getUserByToken(token);
        if(user == null) {
            return null;
        }

        return new AuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(AuthenticationToken.class);
    }
}
