package com.example.ecommerce.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String key, String id, Object data) {
        redisTemplate.opsForHash().put(key, id, data);
    }

    public Object findById(String key, String id) {
        return redisTemplate.opsForHash().get(key, id);
    }

    public void delete(String key, String id) {
        redisTemplate.opsForHash().delete(key, id);
    }
}
