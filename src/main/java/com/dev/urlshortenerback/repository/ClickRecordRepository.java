package com.dev.urlshortenerback.repository;

import com.dev.urlshortenerback.entity.ClickRecordEntity;
import com.dev.urlshortenerback.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClickRecordRepository extends JpaRepository<ClickRecordEntity, Long> {

    List<ClickRecordEntity> findAllByUrlEntity(UrlEntity url);

}
