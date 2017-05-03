package com.adowsky.security;

import com.adowsky.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@AllArgsConstructor
public class HomeLibraryPermissionEvaluator implements PermissionEvaluator {
    private PermissionService permissionService;

    @Override
    public boolean hasPermission(Authentication authentication, Object resource, Object permissionType) {
        if(!(resource instanceof String)) {
            throw new PermissionException("Invalid id type");
        }

        AuthenticationToken token = (AuthenticationToken) authentication;

        if("grant".equals(permissionType)) {
            return token.getUser().getUsername().equals(resource);
        }

        return permissionService.hasAccessTo(token.getUser().getId(), (String)resource);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        if(!(serializable instanceof String)) {
            throw new PermissionException("Invalid id type");
        }

        AuthenticationToken token = (AuthenticationToken) authentication;
        return permissionService.hasAccessTo(token.getUser().getId(), (String)serializable);
    }
}
