package com.dev.urlshortenerback.service;

import com.dev.urlshortenerback.entity.ClickRecordEntity;
import com.dev.urlshortenerback.entity.UrlEntity;

import java.util.List;

public interface ClickRecordService {

    ClickRecordEntity save(ClickRecordEntity entity);
    List<ClickRecordEntity> findAllByUrl(UrlEntity url);

}
