package com.sanglt.webflux_patterns.sec03_orchestrator.services;

import com.sanglt.webflux_patterns.sec03_orchestrator.clients.InventoryClient;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class InventoryOrchestrator extends Orchestrator{

    private final InventoryClient inventoryClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.inventoryClient
                .deduct(context.getInventoryRequest())
                .doOnNext(context::setInventoryResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> Status.SUCCESS.equals(context.getInventoryResponse().getStatus());
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
