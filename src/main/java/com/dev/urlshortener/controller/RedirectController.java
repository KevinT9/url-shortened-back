package com.dev.urlshortener.controller;

import com.dev.urlshortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
public class RedirectController {

    private final UrlShortenerService urlShortenerService;

    public RedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/findByUrlShortener")
    public ResponseEntity<Void> redirectToOriginalUrlFindByUrlShortener(@RequestParam String shortenedUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlShortenerService.getOriginalUrl(shortenedUrl);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{codeGeneratedUrl}")
    public ResponseEntity<Void> redirectToOriginalUrlFindByCodeGenerated(@PathVariable String codeGeneratedUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlShortenerService.getStringOriginalUrl(codeGeneratedUrl);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
