package com.dev.urlshortenerback.service;

import com.dev.urlshortenerback.entity.UrlEntity;
import com.dev.urlshortenerback.entity.UserEntity;

public interface UrlService {
    String shortenUrl(String originalUrl);
    String getOriginalUrl(String shortenedUrl);
    UrlEntity getOriginalUrlFindByCode(String code);
    void delete(UrlEntity urlEntity);
    UrlEntity findUrlEntityByCodeAndUser(String code, UserEntity user);
}
