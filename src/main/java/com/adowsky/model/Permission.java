package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Permission {
    private String grantedToEmail;
    private String resourceOwnerId;
    private String grantedBy;
}
