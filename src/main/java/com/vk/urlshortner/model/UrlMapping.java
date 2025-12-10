package com.vk.urlshortner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "url_mapping", indexes = @Index(columnList = "code", unique = true))
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public UrlMapping(String originalUrl, String code) {
        this.code = code;
        this.originalUrl = originalUrl;
    }
}
