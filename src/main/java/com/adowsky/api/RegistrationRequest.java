package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegistrationRequest {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String passwordHash;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "\\p{L}{3,}")
    private String firstName;
    @NotNull
    @Pattern(regexp = "[\\p{L}-]{3,}")
    private String lastName;
}
