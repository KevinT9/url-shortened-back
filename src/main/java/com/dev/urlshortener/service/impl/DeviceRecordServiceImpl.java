package com.dev.urlshortener.service.impl;

import com.dev.urlshortener.entity.DeviceRecordEntity;
import com.dev.urlshortener.repository.DeviceRecordRepository;
import com.dev.urlshortener.service.DeviceRecordService;
import org.springframework.stereotype.Service;

@Service
public class DeviceRecordServiceImpl implements DeviceRecordService {

    private final DeviceRecordRepository deviceRecordRepository;

    public DeviceRecordServiceImpl(DeviceRecordRepository deviceRecordRepository) {
        this.deviceRecordRepository = deviceRecordRepository;
    }

    @Override
    public DeviceRecordEntity save(DeviceRecordEntity deviceRecordEntity) {
        return deviceRecordRepository.save(deviceRecordEntity);
    }
}
