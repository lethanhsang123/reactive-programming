package com.sanglt.webflux_patterns.sec04.services;

import com.sanglt.webflux_patterns.sec04.clients.ShippingClient;
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
public class ShippingOrchestrator extends Orchestrator {

    private final ShippingClient shippingClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.shippingClient
                .schedule(context.getShippingRequest())
                .doOnNext(context::setShippingResponse)
                .thenReturn(context)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> Objects.nonNull(context.getShippingResponse())
                && Status.SUCCESS.equals(context.getShippingResponse().getStatus());
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
