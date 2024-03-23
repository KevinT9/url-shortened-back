package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {

    @Value("${defaultHost}")
    private String defaultHost;

    private final UrlRepository urlRepository;

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {
        // Lógica para generar URL acortada (puede ser un algoritmo de codificación)
        String code = generateUniqueIdentifier();
        String shortenedUrl = defaultHost + code;
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setShortenedUrl(shortenedUrl);
        urlEntity.setCodeGeneratedUrl(code);

        urlRepository.save(urlEntity);

        return shortenedUrl;
    }

    public String getOriginalUrl(String shortenedUrl) {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByShortenedUrl(shortenedUrl);
        return urlEntityOptional.map(UrlEntity::getOriginalUrl).orElse(null);
    }


    public UrlEntity getOriginalUrlFindByCode(String code) {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByCodeGeneratedUrl(code);
        return urlEntityOptional.orElse(null);
    }

    public String getStringOriginalUrl(String code) {
        UrlEntity urlEntity = getOriginalUrlFindByCode(code);
        return urlEntity.getOriginalUrl();
    }

    private String generateUniqueIdentifier() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
