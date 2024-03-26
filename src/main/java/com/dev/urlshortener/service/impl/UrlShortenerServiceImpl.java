package com.dev.urlshortener.service.impl;

import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.entity.UserEntity;
import com.dev.urlshortener.repository.ClickRecordRepository;
import com.dev.urlshortener.repository.UrlRepository;
import com.dev.urlshortener.service.UrlShortenerService;
import com.dev.urlshortener.service.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Value("${defaultHost}")
    private String defaultHost;

    private final UrlRepository urlRepository;
    private final UsersService userService;
    private final ClickRecordRepository clickRecordRepository;

    public UrlShortenerServiceImpl(UrlRepository urlRepository, UsersService userService, ClickRecordRepository clickRecordRepository) {
        this.urlRepository = urlRepository;
        this.userService = userService;
        this.clickRecordRepository = clickRecordRepository;
    }

    public String shortenUrl(String originalUrl) {
        // Lógica para generar URL acortada (puede ser un algoritmo de codificación)
        String code = generateUniqueIdentifier();
        String shortenedUrl = defaultHost + code;

        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setShortenedUrl(shortenedUrl);
        urlEntity.setCodeGeneratedUrl(code);

        Date currentDate = new Date();
        int tenDaysInMilliseconds = 10 * 24 * 60 * 60 * 1000;
        Date newExpirationDate = new Date(currentDate.getTime() + tenDaysInMilliseconds);
        urlEntity.setExpirationDate(newExpirationDate);

        UserEntity user = userService.getUserEntityAuthenticated();
        urlEntity.setUser(user);

        urlRepository.save(urlEntity);

        return shortenedUrl;
    }

    public String getOriginalUrl(String shortenedUrl) {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByShortenedUrl(shortenedUrl);
        return urlEntityOptional.map(UrlEntity::getOriginalUrl).orElse(null);
    }


    public UrlEntity getOriginalUrlFindByCode(String code) {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByCodeGeneratedUrl(code);

        if (urlEntityOptional.isPresent()) {
            UrlEntity urlEntity = urlEntityOptional.get();

//            ClickRecordEntity clickRecordEntity = new ClickRecordEntity();
//            clickRecordEntity.setUrlEntity(urlEntity);
//            clickRecordEntity.setClickDateTime(new Date());
//            clickRecordEntity.setIpAddress("");
//            clickRecordEntity.setUserAgent("");
//
//            clickRecordRepository.save(clickRecordEntity);
        }

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
