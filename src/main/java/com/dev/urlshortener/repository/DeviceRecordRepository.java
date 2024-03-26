package com.dev.urlshortener.repository;

import com.dev.urlshortener.entity.DeviceRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRecordRepository extends JpaRepository<DeviceRecordEntity, Long> {
}
