package com.sanglt.redisperformance.controllers;

import com.sanglt.redisperformance.entities.Product;
import com.sanglt.redisperformance.services.ProductServiceV2;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products/v2")
public class ProductControllerV2 {

    private final ProductServiceV2 productServiceV2;

    public ProductControllerV2(ProductServiceV2 productServiceV2) {
        this.productServiceV2 = productServiceV2;
    }

    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable int id) {
        return this.productServiceV2.getProduct(id);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable int id, @RequestBody Mono<Product> productMono) {
        return this.productServiceV2.updateProduct(id, productMono);
    }

}
