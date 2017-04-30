package com.adowsky.api;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LoginRequest {
    @NotNull
    @NotBlank
    private String passwordHash;

}
