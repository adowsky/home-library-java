package com.adowsky.security;

import com.adowsky.model.SimpleUser;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AuthenticationToken implements Authentication{
    private String token;
    private SimpleUser user;
    private boolean authenticated;

    AuthenticationToken(String token) {
        this.token = token;
        authenticated = false;
    }

    AuthenticationToken(SimpleUser user) {
        this.user = user;
        authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
