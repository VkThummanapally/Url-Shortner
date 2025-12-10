package com.vk.urlshortner.service;

import com.vk.urlshortner.advice.GlobalExceptionHandler;
import com.vk.urlshortner.exception.NotFoundException;
import com.vk.urlshortner.model.UrlMapping;
import com.vk.urlshortner.repository.UrlRepository;
import com.vk.urlshortner.util.Base62;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UrlShortnerServiceImpl implements UrlShortnerService{
    private final UrlRepository repo;

    private final String domain;

    public UrlShortnerServiceImpl(UrlRepository repo, @Value("${app.domain:http://localhost:8080}}") String domain){
        this.repo = repo;
        this.domain = domain.endsWith("/") ? domain.substring(0, domain.length()-1) : domain;
    }

    @Transactional
    public Optional<UrlMapping> createShortUrl(String originalUrl){
        try {
            UrlMapping temp = new UrlMapping(originalUrl, "");
            UrlMapping saved = repo.save(temp);
            String code = Base62.encode(saved.getId());
            saved.setCode(code);
            return Optional.of(repo.save(saved));
        } catch(Exception ex){
            log.error("Exception while shortening URL: {}", ex.getMessage());
        }
        return Optional.empty();
    }

    public Optional<UrlMapping> findByCode(String code){
        return Optional.ofNullable(repo.findByCode(code).orElseThrow(() -> new NotFoundException("Short URL not found")));
    }

    public String buildShortUrl(String code){
        return domain + "/" + code;
    }
}
