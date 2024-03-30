package com.dev.urlshortenerback.service.impl;

import com.dev.urlshortenerback.entity.DeviceRecordEntity;
import com.dev.urlshortenerback.repository.DeviceRecordRepository;
import com.dev.urlshortenerback.service.DeviceRecordService;
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
