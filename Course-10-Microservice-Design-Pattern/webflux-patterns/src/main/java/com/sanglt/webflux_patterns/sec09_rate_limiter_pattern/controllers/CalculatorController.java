package com.sanglt.webflux_patterns.sec09_rate_limiter_pattern.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec09/calculator")
public class CalculatorController {

    // CPU intensive
    // 5 request / 20 seconds
    @GetMapping("/{input}")
    @RateLimiter(name = "calculator-service", fallbackMethod = "fallback")
    public Mono<ResponseEntity<Integer>> doubleInput(@PathVariable Integer input) {
        return Mono.fromSupplier(() -> input * 2)
                .map(ResponseEntity::ok);
    }

    public Mono<ResponseEntity<String>> fallback(Integer input, Throwable ex) {
        System.out.println("TESTTT");
        return Mono.just(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage()));
    }

}
