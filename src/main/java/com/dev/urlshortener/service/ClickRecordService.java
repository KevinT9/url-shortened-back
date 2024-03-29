package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;

import java.util.List;

public interface ClickRecordService {

    ClickRecordEntity save(ClickRecordEntity entity);
    List<ClickRecordEntity> findAllByUrl(UrlEntity url);

}
