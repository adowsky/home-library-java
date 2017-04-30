package com.adowsky.service;

import com.adowsky.model.User;
import com.adowsky.service.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserEntityMapper {
    UserEntity userToUserEntity(User user);
}
