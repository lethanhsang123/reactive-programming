package com.sanglt.webflux_patterns.sec01_gateway_aggregator.controllers;

import com.sanglt.webflux_patterns.sec01_gateway_aggregator.dtos.ProductAggregator;
import com.sanglt.webflux_patterns.sec01_gateway_aggregator.services.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec01/products")
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
