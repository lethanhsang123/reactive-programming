package com.vinsguru.orderserver.client;

import com.vinsguru.productclient.dto.ProductDto;
import reactor.core.publisher.Mono;

public interface ProductClient {
    Mono<ProductDto> getProductById(final String productId);
}
