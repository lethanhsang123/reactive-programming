package com.vinsguru.productserver.controller;

import com.vinsguru.productclient.dto.ProductDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/products/steams")
public class ProductStreamController {
    private final Flux<ProductDto> flux;

    public ProductStreamController(Flux<ProductDto> flux) {
        this.flux = flux;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductsUpdates() {
        return this.flux;
    }
}
