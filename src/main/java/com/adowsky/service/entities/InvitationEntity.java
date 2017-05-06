package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(schema = "HOMELIBRARY", name = "invitations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long inviterId;
    private String sentTo;
    private String invitationHash;
    private Date creationDate;
    private boolean completed;
}
