package com.sanglt.webflux_patterns.sec03_orchestrator.controllers;

import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrderRequest;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrderResponse;
import com.sanglt.webflux_patterns.sec03_orchestrator.services.OrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec03")
@RequiredArgsConstructor
public class OrderController {

    private final OrchestratorService orchestratorService;

    @PostMapping("/order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> request) {
        return this.orchestratorService.placeOrder(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
