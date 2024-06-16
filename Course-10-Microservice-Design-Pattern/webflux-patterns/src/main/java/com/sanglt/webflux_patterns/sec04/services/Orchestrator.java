package com.sanglt.webflux_patterns.sec04.services;

import com.sanglt.webflux_patterns.sec04.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec04.exceptions.OrderFulfillmentFailure;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Orchestrator {

    public abstract Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context);
    public abstract Predicate<OrchestrationRequestContext> isSuccess();
    public abstract Consumer<OrchestrationRequestContext> cancel();

    protected BiConsumer<OrchestrationRequestContext, SynchronousSink<OrchestrationRequestContext>> statusHandler() {
        return (context, sink) -> {
            if (isSuccess().test(context)) {
                sink.next(context);
            } else {
                sink.error(new OrderFulfillmentFailure());
            }
        };
    }

}
