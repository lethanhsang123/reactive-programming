package com.sanglt.webflux_patterns.sec04.services;

import com.sanglt.webflux_patterns.sec04.dtos.OrchestrationRequestContext;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCancelService {

    private Sinks.Many<OrchestrationRequestContext> sink;
    private Flux<OrchestrationRequestContext> flux;


    private final List<Orchestrator> orchestrators;

    @PostConstruct
    public void init() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = this.sink.asFlux().publishOn(Schedulers.boundedElastic());
        orchestrators.forEach(o -> this.flux.subscribe(o.cancel()));
    }

    public void cancelOrder(OrchestrationRequestContext context) {
        this.sink.tryEmitNext(context);
    }

}
