package com.dev.urlshortener.repository;

import com.dev.urlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByShortenedUrl(String shortenedUrl);
    Optional<UrlEntity> findByCodeGeneratedUrl(String codeGeneratedUrl);
}
