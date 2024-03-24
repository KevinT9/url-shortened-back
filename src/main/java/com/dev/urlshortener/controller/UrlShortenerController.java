package com.dev.urlshortener.controller;

import com.dev.urlshortener.dto.UrlDTO;
import com.dev.urlshortener.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
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
    public ResponseEntity<String> getOriginalUrlByCode(@PathVariable String codeGenerated) {
        String originalUrl = urlShortenerService.getOriginalUrlFindByCode(codeGenerated).getOriginalUrl();
        if (originalUrl != null) {
            return ResponseEntity.ok(originalUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
