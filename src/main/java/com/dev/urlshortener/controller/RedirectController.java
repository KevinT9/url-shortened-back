package com.dev.urlshortener.controller;

import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.DeviceRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.service.ClickRecordService;
import com.dev.urlshortener.service.DeviceRecordService;
import com.dev.urlshortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping
public class RedirectController {

    private final UrlShortenerService urlShortenerService;
    private final ClickRecordService clickRecordService;
    private final DeviceRecordService deviceRecordService;
    private static final UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzer.newBuilder().build();

    public RedirectController(UrlShortenerService urlShortenerService, ClickRecordService clickRecordService, DeviceRecordService deviceRecordService) {
        this.urlShortenerService = urlShortenerService;
        this.clickRecordService = clickRecordService;
        this.deviceRecordService = deviceRecordService;
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
    public ResponseEntity<Void> redirectToOriginalUrlFindByCodeGenerated(@PathVariable String codeGeneratedUrl, HttpServletRequest request,
                                                                         HttpServletResponse response) throws IOException {

        UrlEntity urlEntity = urlShortenerService.getOriginalUrlFindByCode(codeGeneratedUrl);

        if (urlEntity != null) {

            ClickRecordEntity clickRecordEntity = new ClickRecordEntity();
            clickRecordEntity.setUrlEntity(urlEntity);
            clickRecordEntity.setClickDateTime(new Date());

            // Obtener la dirección IP del cliente
            String ipAddress = request.getHeader("X-Forwarded-For");
            System.out.println("X-Forwarded-For " + ipAddress);
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }

            System.out.println("remote add " + ipAddress);
            System.out.println("getRemoteUser  " + request.getRemoteUser());
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
                response.sendRedirect(originalUrl);
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

//        String originalUrl = urlShortenerService.getStringOriginalUrl(codeGeneratedUrl);
//        if (originalUrl != null) {
//            response.sendRedirect(originalUrl);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }
}
