package com.dev.urlshortener.service;

import com.dev.urlshortener.entity.ClickRecordEntity;

public interface ClickRecordService {

    ClickRecordEntity save(ClickRecordEntity entity);

}
