package com.dev.urlshortenerback.repository;

import com.dev.urlshortenerback.entity.DeviceRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRecordRepository extends JpaRepository<DeviceRecordEntity, Long> {
}
