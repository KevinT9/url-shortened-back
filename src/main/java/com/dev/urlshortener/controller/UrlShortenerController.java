package com.dev.urlshortener.controller;

import com.dev.urlshortener.dto.UrlDTO;
import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.DeviceRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.service.ClickRecordService;
import com.dev.urlshortener.service.DeviceRecordService;
import com.dev.urlshortener.service.UrlShortenerService;
import com.dev.urlshortener.service.impl.UrlShortenerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    private final ClickRecordService clickRecordService;
    private final DeviceRecordService deviceRecordService;
    private static final UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzer.newBuilder().build();

    public UrlShortenerController(UrlShortenerServiceImpl urlShortenerService, ClickRecordService clickRecordService, DeviceRecordService deviceRecordService) {
        this.urlShortenerService = urlShortenerService;
        this.clickRecordService = clickRecordService;
        this.deviceRecordService = deviceRecordService;
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
            System.out.println("X-Forwarded-For" + ipAddress);
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }

            System.out.println("remote add" + ipAddress);
            System.out.println("getRemoteUser " + request.getRemoteUser());
            System.out.println("getLocalAddr " + request.getLocalAddr());

            clickRecordEntity.setIpAddress(ipAddress);

            // Obtener el User Agent del cliente
            String userAgentString = request.getHeader("User-Agent");
            clickRecordEntity.setUserAgent(userAgentString);

            clickRecordEntity = clickRecordService.save(clickRecordEntity);

            // Analizar el User Agent para obtener información del dispositivo
            UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);

            DeviceRecordEntity deviceRecordEntity = new DeviceRecordEntity();
            deviceRecordEntity.setClickRecordEntity(clickRecordEntity);
            deviceRecordEntity.setDeviceType(userAgent.getValue(UserAgent.DEVICE_CLASS));
            deviceRecordEntity.setOperatingSystem(userAgent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION));
//            deviceRecordEntity.setScreenResolution(userAgent.getValue(UserAgent.DEVICE_SCREEN_RESOLUTION));

            deviceRecordEntity = deviceRecordService.save(deviceRecordEntity);

            if (clickRecordEntity.getId() != null && deviceRecordEntity.getId() != null) {
                String originalUrl = urlEntity.getOriginalUrl();
                return ResponseEntity.ok(originalUrl);
            }else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
