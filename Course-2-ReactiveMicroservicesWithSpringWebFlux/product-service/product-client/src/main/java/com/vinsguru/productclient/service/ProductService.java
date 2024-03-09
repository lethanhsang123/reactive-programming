package com.vinsguru.productclient.service;

import com.vinsguru.productclient.client.ProductClient;
import com.vinsguru.productclient.dto.ProductDto;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class ProductService {

    public static ProductClient client(WebClient webClient) {
        return new ProductClientImpl(webClient);
    }

    private static class ProductClientImpl implements ProductClient {

        private final WebClient webClient;

        public ProductClientImpl(WebClient webClient) {
            this.webClient = webClient;
        }

        @Override
        public Mono<ProductDto> getById(String id) {
            Objects.requireNonNull(id);
            return this.webClient
                    .get()
                    .uri("/products/{id}", id)
                    .retrieve()
                    .bodyToMono(ProductDto.class);
        }
    }

}
