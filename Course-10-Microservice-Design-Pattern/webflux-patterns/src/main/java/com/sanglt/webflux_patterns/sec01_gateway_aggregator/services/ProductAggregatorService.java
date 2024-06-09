package com.sanglt.webflux_patterns.sec01_gateway_aggregator.services;

import com.sanglt.webflux_patterns.sec01_gateway_aggregator.clients.ProductClient;
import com.sanglt.webflux_patterns.sec01_gateway_aggregator.clients.PromotionClient;
import com.sanglt.webflux_patterns.sec01_gateway_aggregator.clients.ReviewClient;
import com.sanglt.webflux_patterns.sec01_gateway_aggregator.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {

    private final ProductClient productClient;
    private final PromotionClient promotionClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregator> aggregate(Integer id) {
        return Mono.zip(
                this.productClient.getProduct(id),
                this.promotionClient.getPromotion(id),
                this.reviewClient.getReviews(id)
        ).map(t -> toDto(t.getT1(), t.getT2(), t.getT3()));
    }

    private ProductAggregator toDto(ProductResponse product, PromotionResponse promotion, List<Review> reviews) {
        var price = new Price();
        var amountSaved = product.getPrice() * promotion.getDiscount() / 100;
        var discountedPrice = product.getPrice() - amountSaved;
        price.setListPrice(product.getPrice());
        price.setAmountSaved(amountSaved);
        price.setDiscountedPrice(discountedPrice);
        price.setDiscount(promotion.getDiscount());
        price.setEndDate(promotion.getEndDate());
        return ProductAggregator.create(
                product.getId(),
                product.getCategory(),
                product.getDescription(),
                price,
                reviews
        );
    }

}
