package com.dev.urlshortener.controller;

import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.entity.UserEntity;
import com.dev.urlshortener.service.ClickRecordService;
import com.dev.urlshortener.service.UrlService;
import com.dev.urlshortener.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/click")
public class ClickRecordController {

    private final ClickRecordService clickRecordService;
    private final UrlService urlService;
    private final UsersService usersService;

    public ClickRecordController(ClickRecordService clickRecordService, UrlService urlService, UsersService usersService) {
        this.clickRecordService = clickRecordService;
        this.urlService = urlService;

        this.usersService = usersService;
    }

    @GetMapping("/{code}")
    ResponseEntity<List<ClickRecordEntity>> getClickRecords(@PathVariable String code) {

        UserEntity user = usersService.getUserEntityAuthenticated();
        UrlEntity url = urlService.findUrlEntityByCodeAndUser(code, user);
        List<ClickRecordEntity> listClickRecord = clickRecordService.findAllByUrl(url);
        return ResponseEntity.ok(listClickRecord);
    }

}
