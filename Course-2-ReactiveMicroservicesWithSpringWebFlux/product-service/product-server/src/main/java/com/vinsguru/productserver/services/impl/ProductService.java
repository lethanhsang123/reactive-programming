package com.vinsguru.productserver.services.impl;

import com.vinsguru.productclient.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductDto> getAll();

    Mono<ProductDto> getById(String id);

    Mono<ProductDto> insert(Mono<ProductDto> dto);

    Mono<ProductDto> update(String id, Mono<ProductDto> dto);

    Mono<Void> deleteProduct(String id);

    Flux<ProductDto> getProductByPriceRange(int min, int max);

}
