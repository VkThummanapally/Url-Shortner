package com.vk.urlshortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vk.urlshortner.model.UrlMapping;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByCode(String code);
}
