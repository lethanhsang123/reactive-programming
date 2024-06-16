package com.sanglt.webflux_patterns.sec04.clients;

import com.sanglt.webflux_patterns.sec04.dtos.ShippingRequest;
import com.sanglt.webflux_patterns.sec04.dtos.ShippingResponse;
import com.sanglt.webflux_patterns.sec04.dtos.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShippingClient {

    private final WebClient client;
    private static final String SCHEDULE_ENDPOINT = "/schedule";
    private static final String CANCEL_ENDPOINT = "/cancel";

    public ShippingClient(@Value("${sec04.shipping.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return this.callInventoryService(SCHEDULE_ENDPOINT, request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return this.callInventoryService(CANCEL_ENDPOINT, request);
    }

    private Mono<ShippingResponse> callInventoryService(String endPoint, ShippingRequest request) {
        return this.client
                .post()
                .uri(endPoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(buildErrorResponse(request));
    }

    private ShippingResponse buildErrorResponse(ShippingRequest request) {
        return ShippingResponse.create(
                null,
                request.getQuantity(),
                Status.FAILED,
                null,
                null
        );
    }

}
