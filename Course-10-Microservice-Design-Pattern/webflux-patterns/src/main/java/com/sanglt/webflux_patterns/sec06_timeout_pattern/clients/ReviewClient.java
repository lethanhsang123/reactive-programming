package com.sanglt.webflux_patterns.sec06_timeout_pattern.clients;

import com.sanglt.webflux_patterns.sec06_timeout_pattern.dtos.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec06.review.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<List<Review>> getReviews(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .timeout(Duration.ofMillis(500))
                .onErrorReturn(Collections.emptyList());
    }

}
