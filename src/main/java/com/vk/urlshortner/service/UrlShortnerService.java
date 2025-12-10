package com.vk.urlshortner.service;

import com.vk.urlshortner.model.UrlMapping;

import java.util.Optional;

public interface UrlShortnerService {
    Optional<UrlMapping> createShortUrl(String originalUrl);
    Optional<UrlMapping> findByCode(String code);
    String buildShortUrl(String code);
}
