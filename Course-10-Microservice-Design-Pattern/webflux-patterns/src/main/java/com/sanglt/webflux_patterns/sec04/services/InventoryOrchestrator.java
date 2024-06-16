package com.sanglt.webflux_patterns.sec04.services;

import com.sanglt.webflux_patterns.sec04.clients.InventoryClient;
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
public class InventoryOrchestrator extends Orchestrator {

    private final InventoryClient inventoryClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.inventoryClient
                .deduct(context.getInventoryRequest())
                .doOnNext(context::setInventoryResponse)
                .thenReturn(context)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> Objects.nonNull(context.getInventoryResponse())
                && Status.SUCCESS.equals(context.getInventoryResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> Mono.just(context)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getInventoryRequest)
                .flatMap(this.inventoryClient::restore)
                .subscribe();
    }
}
