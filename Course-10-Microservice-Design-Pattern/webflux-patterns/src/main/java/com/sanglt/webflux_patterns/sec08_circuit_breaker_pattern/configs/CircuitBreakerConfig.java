package com.sanglt.webflux_patterns.sec08_circuit_breaker_pattern.configs;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CircuitBreakerConfig {

//    @Bean
    public CircuitBreakerConfigCustomizer reviewService() {
        return CircuitBreakerConfigCustomizer.of("review-service",
                builder -> builder
                        .minimumNumberOfCalls(4)
                        .build());
    }

}
