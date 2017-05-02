package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String accessToken;
    private Date expirationTime;

}
