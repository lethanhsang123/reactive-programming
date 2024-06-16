package com.sanglt.webflux_patterns.sec06_timeout_pattern.clients;

import com.sanglt.webflux_patterns.sec06_timeout_pattern.dtos.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductClient {

    private final WebClient client;

    public ProductClient(@Value("${sec06.product.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

}
