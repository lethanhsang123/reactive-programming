package com.vinsguru.productclient.client;

import com.vinsguru.productclient.dto.ProductDto;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface ProductClient {
    Mono<ProductDto> getById(@PathVariable String id);
}
