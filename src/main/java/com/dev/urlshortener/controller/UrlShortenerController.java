package com.dev.urlshortener.controller;

import com.dev.urlshortener.dto.UrlDTO;
import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.service.ClickRecordService;
import com.dev.urlshortener.service.UrlShortenerService;
import com.dev.urlshortener.service.impl.UrlShortenerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    private final ClickRecordService clickRecordService;

    public UrlShortenerController(UrlShortenerServiceImpl urlShortenerService, ClickRecordService clickRecordService) {
        this.urlShortenerService = urlShortenerService;
        this.clickRecordService = clickRecordService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlDTO urlDTO) {
        String shortenedUrl = urlShortenerService.shortenUrl(urlDTO.getOriginalUrl());
        return ResponseEntity.ok(shortenedUrl);
    }

    @GetMapping("/original")
    public ResponseEntity<String> getOriginalUrl(@RequestParam String shortenedUrl) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortenedUrl);
        if (originalUrl != null) {
            return ResponseEntity.ok(originalUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/original/{codeGenerated}")
    public ResponseEntity<String> getOriginalUrlByCode(@PathVariable String codeGenerated, HttpServletRequest request) {

        UrlEntity urlEntity = urlShortenerService.getOriginalUrlFindByCode(codeGenerated);

        if (urlEntity != null) {

            ClickRecordEntity clickRecordEntity = new ClickRecordEntity();
            clickRecordEntity.setUrlEntity(urlEntity);
            clickRecordEntity.setClickDateTime(new Date());

            // Obtener la dirección IP del cliente
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            clickRecordEntity.setIpAddress(ipAddress);

            // Obtener el User Agent del cliente
            String userAgent = request.getHeader("User-Agent");
            clickRecordEntity.setUserAgent(userAgent);

            clickRecordService.save(clickRecordEntity);

            String originalUrl = urlEntity.getOriginalUrl();
            return ResponseEntity.ok(originalUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
