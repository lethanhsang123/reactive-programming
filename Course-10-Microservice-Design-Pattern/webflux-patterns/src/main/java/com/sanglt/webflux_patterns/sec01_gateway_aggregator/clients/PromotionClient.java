package com.sanglt.webflux_patterns.sec01_gateway_aggregator.clients;

import com.sanglt.webflux_patterns.sec01_gateway_aggregator.dtos.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PromotionClient {

    private final WebClient client;
    private final PromotionResponse promotionResponse = PromotionResponse.create(-1, "no promotion", 0.0, LocalDate.now());

    public PromotionClient(@Value("${sec01.promotion.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<PromotionResponse> getPromotion(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(PromotionResponse.class)
                .onErrorReturn(promotionResponse);
    }

}
