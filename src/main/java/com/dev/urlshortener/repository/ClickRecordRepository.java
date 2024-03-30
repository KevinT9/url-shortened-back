package com.dev.urlshortener.repository;

import com.dev.urlshortener.entity.ClickRecordEntity;
import com.dev.urlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClickRecordRepository extends JpaRepository<ClickRecordEntity, Long> {

    List<ClickRecordEntity> findAllByUrlEntity(UrlEntity url);

}
