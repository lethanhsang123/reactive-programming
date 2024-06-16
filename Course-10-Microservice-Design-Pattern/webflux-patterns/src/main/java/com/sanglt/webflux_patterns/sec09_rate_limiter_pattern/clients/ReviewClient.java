package com.sanglt.webflux_patterns.sec09_rate_limiter_pattern.clients;

import com.sanglt.webflux_patterns.sec09_rate_limiter_pattern.dtos.Review;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec09.review.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @RateLimiter(name = "review-service", fallbackMethod = "fallback")
    public Mono<List<Review>> getReviews(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
//                .doOnNext(list -> put in cache)
//                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
//                .timeout(Duration.ofMillis(300))
//                .onErrorReturn(Collections.emptyList())
                ;
    }

    public Mono<List<Review>> fallback(Integer id, Throwable ex) {
        return Mono.just(Collections.emptyList());
    }

}
