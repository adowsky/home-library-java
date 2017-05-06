package com.adowsky.service;

import com.adowsky.service.entities.InvitationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends CrudRepository<InvitationEntity, Long> {
    Optional<InvitationEntity> findFirstByInvitationHash(String invitationHash);

}
