package com.dev.urlshortenerback.service;

import com.dev.urlshortenerback.entity.UserEntity;

public interface UsersService {

    UserEntity getUserEntityAuthenticated();
    UserEntity saveUser(UserEntity user);
    UserEntity modifyUser(UserEntity user);
    UserEntity findByUsername(String username);
    UserEntity findUserByUsernameAndPassword(String username, String password);
}
