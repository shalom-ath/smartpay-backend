package com.shalom.smartpay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration CACHE_TTL = Duration.ofMinutes(10);

    public void cacheBalance(String email, BigDecimal balance) {

        redisTemplate.opsForValue().set(
                "wallet:" + email,
                balance,
                CACHE_TTL
        );
    }

    public BigDecimal getCachedBalance(String email) {

        Object value = redisTemplate.opsForValue().get("wallet:" + email);

        if (value == null) {
            return null;
        }

        return (BigDecimal) value;
    }

    public void evictBalance(String email) {
        redisTemplate.delete("wallet:" + email);
    }
}