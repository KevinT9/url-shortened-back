package com.dev.urlshortenerback.controller;

import com.dev.urlshortenerback.entity.ClickRecordEntity;
import com.dev.urlshortenerback.entity.DeviceRecordEntity;
import com.dev.urlshortenerback.entity.UrlEntity;
import com.dev.urlshortenerback.service.ClickRecordService;
import com.dev.urlshortenerback.service.DeviceRecordService;
import com.dev.urlshortenerback.service.UrlService;
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

    private final UrlService urlService;
    private final ClickRecordService clickRecordService;
    private final DeviceRecordService deviceRecordService;
    private static final UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzer.newBuilder().build();

    public RedirectController(UrlService urlService, ClickRecordService clickRecordService, DeviceRecordService deviceRecordService) {
        this.urlService = urlService;
        this.clickRecordService = clickRecordService;
        this.deviceRecordService = deviceRecordService;
    }


    @GetMapping("/findByUrlShortener")
    public ResponseEntity<Void> redirectToOriginalUrlFindByUrlShortener(@RequestParam String shortenedUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortenedUrl);
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

        UrlEntity urlEntity = urlService.getOriginalUrlFindByCode(codeGeneratedUrl);

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
            String userAgentString = request.getHeader("User-Agent");
            clickRecordEntity.setUserAgent(userAgentString);

            clickRecordEntity = clickRecordService.save(clickRecordEntity);

            UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);

            DeviceRecordEntity deviceRecordEntity = new DeviceRecordEntity();
            deviceRecordEntity.setClickRecordEntity(clickRecordEntity);
            deviceRecordEntity.setDeviceType(userAgent.getValue(UserAgent.DEVICE_CLASS));
            deviceRecordEntity.setOperatingSystem(userAgent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION));

            deviceRecordEntity = deviceRecordService.save(deviceRecordEntity);

            if (clickRecordEntity.getId() != null && deviceRecordEntity.getId() != null) {
                String originalUrl = urlEntity.getOriginalUrl();
                response.sendRedirect(originalUrl);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
