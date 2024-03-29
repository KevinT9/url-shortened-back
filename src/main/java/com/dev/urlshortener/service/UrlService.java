package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.entity.UserEntity;

public interface UrlService {
    UrlEntity findUrlEntityByCodeAndUser(String code, UserEntity user);
}
