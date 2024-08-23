package com.sanglt.user.configs;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("grpc.server.thread-pool")
public final class GrpcServerThreadPoolConfig {

    private final int coreSize;
    private final int maxSize;
    private final int queueCapacity;
    private final long keepAlive;

    public GrpcServerThreadPoolConfig(int coreSize, int maxSize, int queueCapacity, long keepAlive) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.queueCapacity = queueCapacity;
        this.keepAlive = keepAlive;
    }
}


