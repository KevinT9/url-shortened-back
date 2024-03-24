package com.dev.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "url")
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false)
    private String shortenedUrl;

    @Column(nullable = false)
    private String codeGeneratedUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

