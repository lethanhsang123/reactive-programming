package com.sanglt.webflux_patterns.sec03_orchestrator.services;

import com.sanglt.webflux_patterns.sec03_orchestrator.clients.UserClient;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class PaymentOrchestrator extends Orchestrator {

    private final UserClient userClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.userClient
                .deduct(context.getPaymentRequest())
                .doOnNext(context::setPaymentResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Status.SUCCESS.equals(ctx.getPaymentResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> Mono.just(context)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getPaymentRequest)
                .flatMap(this.userClient::refund)
                .subscribe();
    }
}
