package com.sanglt.redisperformance.controllers;

import com.sanglt.redisperformance.services.BusinessMetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/products/metrics")
public class BusinessMetricsController {

    private final BusinessMetricsService businessMetricsService;

    public BusinessMetricsController(BusinessMetricsService businessMetricsService) {
        this.businessMetricsService = businessMetricsService;
    }

    @GetMapping
    public Flux<Map<Integer, Double>> getMetrics() {
        return this.businessMetricsService.top3Products()
                .repeatWhen(l -> Flux.interval(Duration.ofSeconds(3)));
    }

}
