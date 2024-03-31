package com.sanglt.redisperformance.services;

import com.sanglt.redisperformance.entities.Product;
import com.sanglt.redisperformance.repositories.ProductRepository;
import com.sanglt.redisperformance.services.utils.CacheTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProductServiceV2 {

    private final CacheTemplate<Integer, Product> cacheTemplate;
    private final ProductVisitService productVisitService;

    public Mono<Product> getProduct(int id) {
        return cacheTemplate.get(id)
                .doFirst(() -> this.productVisitService.addVisit(id));
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return productMono.flatMap(p -> this.cacheTemplate.update(id, p));
    }

    public Mono<Void> deleteProduct(int id) {
        return this.cacheTemplate.delete(id);
    }


}
