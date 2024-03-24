package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.UserEntity;

public interface UsersService {

    UserEntity getAuthenticatedUsername();
    UserEntity saveUser(UserEntity user);
}
