package com.dev.urlshortener.controller;

import com.dev.urlshortener.dto.UrlDTO;
import com.dev.urlshortener.entity.UserEntity;
import com.dev.urlshortener.service.UrlShortenerService;
import com.dev.urlshortener.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    private final UsersService userService;

    public UrlShortenerController(UrlShortenerService urlShortenerService, UsersService userService) {
        this.urlShortenerService = urlShortenerService;
        this.userService = userService;
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
