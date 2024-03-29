package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.entity.UserEntity;

public interface UrlService {
    String shortenUrl(String originalUrl);
    String getOriginalUrl(String shortenedUrl);
    UrlEntity getOriginalUrlFindByCode(String code);
    void delete(UrlEntity urlEntity);
    UrlEntity findUrlEntityByCodeAndUser(String code, UserEntity user);
}
