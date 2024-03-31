package com.sanglt.redisson.test.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;

import java.util.Objects;

public class RedissonConfigurer {

    private RedissonClient client;

    public RedissonConfigurer() {}

    public RedissonReactiveClient getRedissonReactiveClient() {
        return getInstance().reactive();
    }

    public RedissonClient getInstance() {
        if (Objects.isNull(this.client)) {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://localhost:6379");
            client = Redisson.create(config);
        }
        return client;
    }

}
