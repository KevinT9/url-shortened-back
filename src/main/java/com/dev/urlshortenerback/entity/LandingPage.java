package com.dev.urlshortenerback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "landing_page")
public class LandingPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UrlEntity urlEntity;

    @Lob
    private String content; // HTML personalizado o referencia a una plantilla.

    @Column
    private String pageTitle; // Título de la página de aterrizaje.

    // Otros campos como metadatos, CSS personalizado, etc.

}
