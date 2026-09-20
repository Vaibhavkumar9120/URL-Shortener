package com.urlshortener.dto;

import java.time.LocalDateTime;

public class UrlResponse {

    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;

    public UrlResponse(
            String originalUrl,
            String shortUrl,
            LocalDateTime createdAt) {

        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdAt = createdAt;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}