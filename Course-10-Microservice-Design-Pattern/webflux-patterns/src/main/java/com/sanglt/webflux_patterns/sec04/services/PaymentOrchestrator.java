package com.sanglt.webflux_patterns.sec04.services;

import com.sanglt.webflux_patterns.sec04.clients.UserClient;
import com.sanglt.webflux_patterns.sec04.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec04.dtos.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class PaymentOrchestrator extends Orchestrator {

    private final UserClient userClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        System.out.println("test");
        return this.userClient
                .deduct(context.getPaymentRequest())
                .doOnNext(context::setPaymentResponse)
                .thenReturn(context)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Objects.nonNull(ctx.getPaymentResponse())
                && Status.SUCCESS.equals(ctx.getPaymentResponse().getStatus());
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
