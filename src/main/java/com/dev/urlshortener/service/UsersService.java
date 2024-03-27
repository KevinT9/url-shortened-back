package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.UserEntity;

public interface UsersService {

    UserEntity getUserEntityAuthenticated();
    UserEntity saveUser(UserEntity user);
    UserEntity findByUsername(String username);
    UserEntity findUserByUsernameAndPassword(String username, String password);
}
