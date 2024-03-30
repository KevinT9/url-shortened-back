package com.dev.urlshortener.service.impl;

import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;
import com.dev.urlshortener.repository.ClickRecordRepository;
import com.dev.urlshortener.service.ClickRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClickRecordServiceImpl implements ClickRecordService {

    private final ClickRecordRepository clickRecordRepository;

    public ClickRecordServiceImpl(ClickRecordRepository clickRecordRepository) {
        this.clickRecordRepository = clickRecordRepository;
    }

    @Override
    public ClickRecordEntity save(ClickRecordEntity entity) {
        return clickRecordRepository.save(entity);
    }

    @Override
    public List<ClickRecordEntity> findAllByUrl(UrlEntity url) {
        return clickRecordRepository.findAllByUrlEntity(url);
    }
}
