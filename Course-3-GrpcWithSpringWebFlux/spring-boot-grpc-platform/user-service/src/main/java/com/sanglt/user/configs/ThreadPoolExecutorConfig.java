package com.sanglt.user.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration(proxyBeanMethods = false)
public class ThreadPoolExecutorConfig {

    private final GrpcServerThreadPoolConfig grpcServerThreadPoolConfig;

    public ThreadPoolExecutorConfig(GrpcServerThreadPoolConfig grpcServerThreadPoolConfig) {
        this.grpcServerThreadPoolConfig = grpcServerThreadPoolConfig;
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                grpcServerThreadPoolConfig.getCoreSize(),
                grpcServerThreadPoolConfig.getMaxSize(),
                grpcServerThreadPoolConfig.getKeepAlive(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(grpcServerThreadPoolConfig.getQueueCapacity())
        );
    }

}
