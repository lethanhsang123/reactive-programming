package com.vinsguru.orderserver.clients;


import com.vinsguru.orderserver.dtos.responses.clients.ProductDto;
import reactor.core.publisher.Mono;

public interface ProductClient {
    Mono<ProductDto> getProductById(final String productId);
}
