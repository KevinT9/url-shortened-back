package com.dev.urlshortener.service.impl;

import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.entity.UserEntity;
import com.dev.urlshortener.repository.UrlRepository;
import com.dev.urlshortener.service.UrlService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlServiceimpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceimpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlEntity findUrlEntityByCodeAndUser(String code, UserEntity user) {
        Optional<UrlEntity> urlEntity = urlRepository.findByCodeGeneratedUrlAndUser(code, user);
        return urlEntity.orElse(null);
    }
}
