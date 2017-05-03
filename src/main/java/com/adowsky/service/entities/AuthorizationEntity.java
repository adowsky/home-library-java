package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "AUTH", schema = "HOMELIBRARY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationEntity {
    @Id
    private Long id;
    private String token;
    private Date expirationDate;
}
