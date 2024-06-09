package com.vinsguru.orderserver.clients.impl;

import com.vinsguru.orderserver.clients.ProductClient;
import com.vinsguru.orderserver.dtos.responses.clients.ProductDto;
import com.vinsguru.orderserver.utils.CommonParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
        Objects.requireNonNull(productId);
        return this.webClient
                .get()
                .uri(CommonParam.ServiceURI.ProductService.GET_PRODUCT_BY_ID, productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

}
