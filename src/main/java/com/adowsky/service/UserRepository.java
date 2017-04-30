package com.adowsky.service;

import com.adowsky.service.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> getByUsername(String username);
}
