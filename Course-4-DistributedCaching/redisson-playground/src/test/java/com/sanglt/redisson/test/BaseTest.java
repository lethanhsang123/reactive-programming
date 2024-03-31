package com.sanglt.redisson.test;

import com.sanglt.redisson.test.config.RedissonConfigurer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonReactiveClient;

import java.util.Objects;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private final RedissonConfigurer redissonConfigurer = new RedissonConfigurer();
    protected RedissonReactiveClient client;

    @BeforeAll
    public void setClient() {
        if (Objects.isNull(client)) {
            client = redissonConfigurer.getRedissonReactiveClient();
        }
    }

    @AfterAll
    public void shutdown() {
        if (Objects.nonNull(this.client)) {
            this.client.shutdown();
        }
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
