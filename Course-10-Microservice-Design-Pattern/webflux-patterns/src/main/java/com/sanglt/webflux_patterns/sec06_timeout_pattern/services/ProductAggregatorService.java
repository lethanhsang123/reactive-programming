package com.sanglt.webflux_patterns.sec06_timeout_pattern.services;

import com.sanglt.webflux_patterns.sec06_timeout_pattern.clients.ProductClient;
import com.sanglt.webflux_patterns.sec06_timeout_pattern.clients.ReviewClient;
import com.sanglt.webflux_patterns.sec06_timeout_pattern.dtos.ProductAggregator;
import com.sanglt.webflux_patterns.sec06_timeout_pattern.dtos.Product;
import com.sanglt.webflux_patterns.sec06_timeout_pattern.dtos.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {

    private final ProductClient productClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregator> aggregate(Integer id) {
        return Mono.zip(
                this.productClient.getProduct(id),
                this.reviewClient.getReviews(id)
        ).map(t -> toDto(t.getT1(), t.getT2()));
    }

    private ProductAggregator toDto(Product product, List<Review> reviews) {
        return ProductAggregator.create(
                product.getId(),
                product.getCategory(),
                product.getDescription(),
                reviews
        );
    }

}
