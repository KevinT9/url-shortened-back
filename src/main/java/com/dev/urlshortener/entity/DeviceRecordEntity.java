package com.dev.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "device_record")
public class DeviceRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ClickRecordEntity clickRecordEntity;

    private String deviceType;
    private String operatingSystem;
    private String screenResolution;
}
