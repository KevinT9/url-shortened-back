package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.UrlEntity;

public interface UrlShortenerService {
    String shortenUrl(String originalUrl);
    String getStringOriginalUrl(String code);
    String getOriginalUrl(String shortenedUrl);
    UrlEntity getOriginalUrlFindByCode(String code);
}
