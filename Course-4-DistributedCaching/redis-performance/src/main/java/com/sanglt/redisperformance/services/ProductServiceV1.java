package com.sanglt.redisperformance.services;

import com.sanglt.redisperformance.entities.Product;
import com.sanglt.redisperformance.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceV1 {

    private final ProductRepository productRepository;

    public ProductServiceV1(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> getProduct(int id) {
        return this.productRepository.findById(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return this.productRepository.findById(id)
                .flatMap(p -> productMono.doOnNext(pr -> pr.setId(id)))
                .flatMap(this.productRepository::save);
    }

}
