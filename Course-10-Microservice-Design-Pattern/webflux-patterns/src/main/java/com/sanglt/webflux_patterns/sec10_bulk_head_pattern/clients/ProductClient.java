package com.sanglt.webflux_patterns.sec10_bulk_head_pattern.clients;

import com.sanglt.webflux_patterns.sec10_bulk_head_pattern.dtos.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class ProductClient {

    private final WebClient client;

    public ProductClient(@Value("${sec10.product.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(Product.class)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .timeout(Duration.ofMillis(300))
                .onErrorResume(ex -> Mono.empty());
    }

}
