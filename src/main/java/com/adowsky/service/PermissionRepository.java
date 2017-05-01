package com.adowsky.service;

import com.adowsky.service.entities.PermissionEntity;
import com.adowsky.service.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PermissionRepository extends CrudRepository<PermissionEntity, Long>{

    List<PermissionEntity> findAllByGrantedTo(UserEntity userEntity);
}
