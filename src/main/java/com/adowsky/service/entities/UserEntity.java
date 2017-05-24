package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "users", schema = "HOMELIBRARY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    public UserEntity(Long id) {
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String surname;
    private String registrationHash;
    private boolean confirmed;
    private Timestamp creationDate;
}
