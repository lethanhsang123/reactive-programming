package com.sanglt.redisperformance.controllers;

import com.sanglt.redisperformance.entities.Product;
import com.sanglt.redisperformance.services.ProductServiceV1;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products/v1")
public class ProductControllerV1 {

    private final ProductServiceV1 productServiceV1;

    public ProductControllerV1(ProductServiceV1 productServiceV1) {
        this.productServiceV1 = productServiceV1;
    }

    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable int id) {
        return this.productServiceV1.getProduct(id);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable int id, @RequestBody Mono<Product> productMono) {
        return this.productServiceV1.updateProduct(id, productMono);
    }

}
