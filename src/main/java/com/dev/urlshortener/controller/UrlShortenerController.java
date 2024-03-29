package com.dev.urlshortener.controller;

import com.dev.urlshortener.dto.UrlDTO;
import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {
    private final UrlService urlService;

    public UrlShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlDTO urlDTO) {
        String shortenedUrl = urlService.shortenUrl(urlDTO.getOriginalUrl());
        return ResponseEntity.ok(shortenedUrl);
    }

    @GetMapping("/original")
    public ResponseEntity<String> getOriginalUrl(@RequestParam String shortenedUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenedUrl);
        if (originalUrl != null) {
            return ResponseEntity.ok(originalUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/original/{codeGenerated}")
    public ResponseEntity<String> getOriginalUrlByCode(@PathVariable String codeGenerated) {

        UrlEntity urlEntity = urlService.getOriginalUrlFindByCode(codeGenerated);

        if (urlEntity != null) {
            String originalUrl = urlEntity.getOriginalUrl();
            return ResponseEntity.ok(originalUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codeGenerated}")
    public ResponseEntity<?> deleteUrl(@PathVariable String codeGenerated) {
        UrlEntity url = urlService.getOriginalUrlFindByCode(codeGenerated);

        urlService.delete(url);
        return ResponseEntity.ok().build();
    }
}
