package com.dev.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "click_record")
public class ClickRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UrlEntity urlEntity;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private Date clickDateTime;

    private String userAgent;
}
