package com.vk.urlshortner.controller;

import com.vk.urlshortner.dto.ShortenRequest;
import com.vk.urlshortner.dto.ShortenResponse;
import com.vk.urlshortner.exception.NotFoundException;
import com.vk.urlshortner.model.UrlMapping;
import com.vk.urlshortner.service.UrlShortnerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private final UrlShortnerServiceImpl service;

    public UrlController(UrlShortnerServiceImpl service){this.service = service;}

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest request){
        try {
            UrlMapping mapping = service.createShortUrl(request.getUrl()).orElseThrow(() -> new RuntimeException("Unable to shorten the URL"));
            String shortUrl = service.buildShortUrl(mapping.getCode());
            return ResponseEntity.ok(new ShortenResponse(mapping.getCode(), shortUrl));
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/{code}")
        public ResponseEntity<Void> redirectTo(@PathVariable String code){
        UrlMapping mapping = service.findByCode(code).orElseThrow();
        return ResponseEntity.status(302).header("Location", mapping.getOriginalUrl()).build();
    }

    @GetMapping("/api/info/{code}")
    public ResponseEntity<UrlMapping> getInfo(@PathVariable String code){
        return ResponseEntity.ok(service.findByCode(code).orElseThrow());
    }

}
