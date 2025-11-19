package com.agentweave.backend.rag;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ContextCacheService {

    private final StringRedisTemplate redisTemplate;

    public ContextCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheContext(String key, String content) {
        redisTemplate.opsForValue().set(key, content, Duration.ofMinutes(30));
    }

    public String getContext(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
