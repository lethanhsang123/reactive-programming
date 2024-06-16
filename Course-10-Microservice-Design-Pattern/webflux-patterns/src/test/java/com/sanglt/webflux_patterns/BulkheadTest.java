package com.sanglt.webflux_patterns;

import com.sanglt.webflux_patterns.sec10_bulk_head_pattern.dtos.ProductAggregator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BulkheadTest {

    private WebClient client;

    @Test
    public void concurrentUsersTest() {
        StepVerifier.create(Flux.merge(fibRequests(), productRequests()))
                .verifyComplete();
    }

    @BeforeAll
    public void setClient() {
        this.client = WebClient.builder()
                .baseUrl("http://localhost:8080/")
                .build();
    }

    private Mono<Void> fibRequests() {
        return Flux.range(1, 4)
                .flatMap(i -> this.client.get().uri("/fib/46").retrieve().bodyToMono(Long.class))
                .doOnNext(this::print)
                .then();
    }

    private Mono<Void> productRequests() {
        return Mono.delay(Duration.ofMillis(100))
                .thenMany(Flux.range(1, 4))
                .flatMap(i -> this.client.get().uri("/products/1").retrieve().bodyToMono(ProductAggregator.class))
                .map(ProductAggregator::getCategory)
                .doOnNext(this::print)
                .then();
    }

    private void print(Object o) {
        System.out.println(LocalDateTime.now() + " : " + o);
    }

}
