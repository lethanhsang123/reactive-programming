package com.sanglt.user.configs;

import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration(proxyBeanMethods = false)
public class GrpcServerConfiguration {

    private final ThreadPoolExecutor threadPoolExecutor;

    public GrpcServerConfiguration(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Bean
    public GrpcServerConfigurer grpcServerConfigurer() {
        return serverBuilder -> {

            serverBuilder.executor(threadPoolExecutor);

            // Configure keepalive time - how often the server sends keepalive pings
            serverBuilder.keepAliveTime(60, TimeUnit.SECONDS);

            // Configure keepalive timeout - how long the server waits for a ping response
            serverBuilder.keepAliveTimeout(20, TimeUnit.SECONDS);

            // Allow keepalive pings even when there are no outstanding RPCs
            serverBuilder.permitKeepAliveWithoutCalls(true);

            // Set the maximum number of keepalive pings without receiving a reply
            serverBuilder.permitKeepAliveTime(5, TimeUnit.SECONDS);
        };
    }






}
