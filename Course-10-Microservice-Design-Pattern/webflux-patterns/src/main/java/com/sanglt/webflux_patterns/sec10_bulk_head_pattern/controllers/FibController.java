package com.sanglt.webflux_patterns.sec10_bulk_head_pattern.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec10/fib")
public class FibController {

    // CPU intensive
    // 5 request / 20 seconds
    @GetMapping("/{input}")
    @RateLimiter(name = "calculator-service", fallbackMethod = "fallback")
    public Mono<ResponseEntity<Long>> doubleInput(@PathVariable Long input) {
        return Mono.fromSupplier(() -> findFib(input))
                .map(ResponseEntity::ok);
    }

    private Long findFib(Long input) {
        if (input < 2) return input;
        else return findFib(input - 1) + findFib(input - 2);
    }

}
