package com.sanglt.webflux_patterns.sec03_orchestrator.clients;

import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.PaymentRequest;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.PaymentResponse;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient client;
    private static final String DEDUCT_ENDPOINT = "/deduct";
    private static final String REFUND_ENDPOINT = "/refund";

    public UserClient(@Value("${sec03.user.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest request) {
        return this.callUserService(DEDUCT_ENDPOINT, request);
    }

    public Mono<PaymentResponse> refund(PaymentRequest request) {
        return this.callUserService(REFUND_ENDPOINT, request);
    }

    private Mono<PaymentResponse> callUserService(String endPoint, PaymentRequest request) {
        return this.client
                .post()
                .uri(endPoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(buildErrorResponse(request));
    }

    private PaymentResponse buildErrorResponse(PaymentRequest request) {
        return PaymentResponse.create(
                request.getUserId(),
                null,
                request.getAmount(),
                Status.FAILED
        );
    }

}
