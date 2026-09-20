package com.urlshortener.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.urlshortener.dto.CreateUrlRequest;
import com.urlshortener.dto.UrlResponse;
import com.urlshortener.entity.ShortUrl;
import com.urlshortener.entity.User;
import com.urlshortener.exception.ShortUrlNotFoundException;
import com.urlshortener.repository.ShortUrlRepository;
import com.urlshortener.repository.UserRepository;

@Service
public class UrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final UserRepository userRepository;

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final Random random = new Random();

    public UrlService(
            ShortUrlRepository shortUrlRepository,
            UserRepository userRepository) {

        this.shortUrlRepository = shortUrlRepository;
        this.userRepository = userRepository;
    }

    public UrlResponse createShortUrl(
            CreateUrlRequest request,
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String shortCode = generateUniqueCode();

        ShortUrl shortUrl = new ShortUrl();

        shortUrl.setOriginalUrl(
                request.getOriginalUrl());

        shortUrl.setShortCode(shortCode);

        shortUrl.setCreatedAt(
                LocalDateTime.now());

        shortUrl.setUser(user);

        ShortUrl saved =
                shortUrlRepository.save(shortUrl);

        return new UrlResponse(
                saved.getOriginalUrl(),
                "http://localhost:8080/r/"
                        + saved.getShortCode(),
                saved.getCreatedAt());
    }

    public String getOriginalUrl(String shortCode) {

        ShortUrl shortUrl =
                shortUrlRepository
                        .findByShortCode(shortCode)
                        .orElseThrow(() ->
                                new ShortUrlNotFoundException(
                                        "Short URL not found"));

        return shortUrl.getOriginalUrl();
    }

    private String generateUniqueCode() {

        String code;

        do {
            StringBuilder builder =
                    new StringBuilder();

            for (int i = 0; i < 6; i++) {

                int index =
                        random.nextInt(
                                CHARACTERS.length());

                builder.append(
                        CHARACTERS.charAt(index));
            }

            code = builder.toString();

        } while (
                shortUrlRepository.existsByShortCode(code)
        );

        return code;
    }
}