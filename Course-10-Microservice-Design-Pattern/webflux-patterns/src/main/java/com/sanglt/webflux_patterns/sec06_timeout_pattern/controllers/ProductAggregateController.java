package com.sanglt.webflux_patterns.sec06_timeout_pattern.controllers;

import com.sanglt.webflux_patterns.sec06_timeout_pattern.dtos.ProductAggregator;
import com.sanglt.webflux_patterns.sec06_timeout_pattern.services.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec06/products")
@RequiredArgsConstructor
public class ProductAggregateController {

    private final ProductAggregatorService productAggregatorService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductAggregator>> getProductAggregate(@PathVariable Integer id) {
        return this.productAggregatorService
                .aggregate(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

}
