package com.adowsky.service;

import com.adowsky.service.entities.AuthorizationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AuthorizationRepository extends CrudRepository <AuthorizationEntity, Long> {

}
