package com.sanglt.webflux_patterns.sec04.clients;

import com.sanglt.webflux_patterns.sec04.dtos.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {
    private final WebClient client;

    public ProductClient(@Value("${sec04.product.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return this.client
                .get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorResume(ex -> Mono.empty());
    }
}
