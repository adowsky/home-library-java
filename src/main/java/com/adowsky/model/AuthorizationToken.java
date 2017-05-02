package com.adowsky.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class AuthorizationToken {
    private String token;
    private Date expirationDate;

}
