package com.sanglt.webflux_patterns.sec03_orchestrator.services;

import com.sanglt.webflux_patterns.sec03_orchestrator.clients.ShippingClient;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ShippingOrchestrator extends Orchestrator {

    private final ShippingClient shippingClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.shippingClient
                .schedule(context.getShippingRequest())
                .doOnNext(context::setShippingResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> Status.SUCCESS.equals(context.getShippingResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> Mono.just(context)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getShippingRequest)
                .flatMap(this.shippingClient::cancel)
                .subscribe();
    }
}
