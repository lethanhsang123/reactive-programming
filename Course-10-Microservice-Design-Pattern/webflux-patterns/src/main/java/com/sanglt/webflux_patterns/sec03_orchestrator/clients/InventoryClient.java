package com.sanglt.webflux_patterns.sec03_orchestrator.clients;

import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryClient {

    private final WebClient client;
    private static final String DEDUCT_ENDPOINT = "/deduct";
    private static final String RESTORE_ENDPOINT = "/restore";

    public InventoryClient(@Value("${sec03.inventory.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return this.callInventoryService(DEDUCT_ENDPOINT, request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return this.callInventoryService(RESTORE_ENDPOINT, request);
    }

    private Mono<InventoryResponse> callInventoryService(String endPoint, InventoryRequest request) {
        return this.client
                .post()
                .uri(endPoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(buildErrorResponse(request));
    }

    private InventoryResponse buildErrorResponse(InventoryRequest request) {
        return InventoryResponse.create(
                request.getProductId(),
                request.getQuantity(),
                null,
                Status.FAILED
        );
    }

}
