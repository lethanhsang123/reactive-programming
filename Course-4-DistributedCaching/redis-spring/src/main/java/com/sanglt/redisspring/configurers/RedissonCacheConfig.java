package com.sanglt.redisspring.configurers;

import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RedissonCacheConfig {

    private final RedissonClient redissonClient;

    @Bean
    public CacheManager cacheManager() {
        return new RedissonSpringCacheManager(this.redissonClient);
    }

}
