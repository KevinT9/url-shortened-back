package com.dev.urlshortener.repository;

import com.dev.urlshortener.entity.ClickRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickRecordRepository extends JpaRepository<ClickRecordEntity, Long> {
}
