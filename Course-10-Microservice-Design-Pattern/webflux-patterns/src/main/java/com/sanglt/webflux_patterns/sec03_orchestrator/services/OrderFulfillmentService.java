package com.sanglt.webflux_patterns.sec03_orchestrator.services;

import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final List<Orchestrator> orchestrators;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext context) {
        var list = orchestrators.stream()
                .map(o -> o.create(context))
                .toList();
        return Mono.zip(list, a -> a[0])
                .cast(OrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext context) {
        var allSuccess = this.orchestrators.stream().allMatch(o -> o.isSuccess().test(context));
        var status = allSuccess ? Status.SUCCESS : Status.FAILED;
        context.setStatus(status);
    }

}
