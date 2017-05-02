package com.adowsky.service;

import com.adowsky.service.entities.PermissionEntity;
import com.adowsky.service.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface PermissionRepository extends CrudRepository<PermissionEntity, Long>{

    List<PermissionEntity> findAllByGrantedTo(UserEntity userEntity);
    Optional<PermissionEntity> findFirstByOwnerAndGrantedTo(UserEntity owner, UserEntity userEntity);
}
