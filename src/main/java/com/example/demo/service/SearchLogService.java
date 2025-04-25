package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SearchLogService {

    private static final String SEARCH_LOG_KEY = "search_logs";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void logSearch(Integer prodId) {
        redisTemplate.opsForZSet().incrementScore(SEARCH_LOG_KEY, prodId.toString(), 1);
    }

    public Set<ZSetOperations.TypedTuple<Object>> getTopSearchedProducts(int limit) {
        return redisTemplate.opsForZSet()
                .reverseRangeWithScores(SEARCH_LOG_KEY, 0, limit - 1);
    }
}
