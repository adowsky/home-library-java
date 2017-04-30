package com.adowsky.service;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {
    @Bean
    public UserEntityMapper userEntityMapper() {
        return Mappers.getMapper(UserEntityMapper.class);
    }
}
