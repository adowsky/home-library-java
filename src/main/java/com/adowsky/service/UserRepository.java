package com.adowsky.service;

import com.adowsky.service.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> getByUsername(String username);
    Optional<UserEntity> getByEmail(String email);
    Optional<UserEntity> getByRegistrationHash(String registrationHash);
    List<UserEntity> findAllByConfirmed(boolean confirmed);
}
