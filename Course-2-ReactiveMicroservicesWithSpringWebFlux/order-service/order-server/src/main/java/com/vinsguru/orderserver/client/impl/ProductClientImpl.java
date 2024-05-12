package com.vinsguru.orderserver.client.impl;

import com.vinsguru.orderserver.client.ProductClient;
import com.vinsguru.productclient.dto.ProductDto;
import com.vinsguru.productclient.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClientImpl implements ProductClient {

    private final WebClient webClient;

    public ProductClientImpl(@Value("${product.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public Mono<ProductDto> getProductById(final String productId) {
        return ProductService.client(this.webClient).getById(productId);
    }

    public Flux<ProductDto> getAllProducts() {
        return this.webClient
                .get()
                .uri("all")
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
