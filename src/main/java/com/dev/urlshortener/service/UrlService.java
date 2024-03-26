package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.UrlEntity;

public interface UrlService {
    UrlEntity findUrlEntityByCode(String code);
}
