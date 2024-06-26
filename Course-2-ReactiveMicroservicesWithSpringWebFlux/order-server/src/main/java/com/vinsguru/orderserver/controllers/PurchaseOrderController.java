package com.vinsguru.orderserver.controllers;


import com.vinsguru.orderserver.dtos.requests.PurchaseOrderRequestDto;
import com.vinsguru.orderserver.dtos.responses.PurchaseOrderResponseDto;
import com.vinsguru.orderserver.services.OrderFulfillmentService;
import com.vinsguru.orderserver.services.OrderQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/orders")
public class PurchaseOrderController {

    private final OrderFulfillmentService orderFulfillmentService;

    private final OrderQueryService orderQueryService;

    public PurchaseOrderController(OrderFulfillmentService orderFulfillmentService, OrderQueryService orderQueryService) {
        this.orderFulfillmentService = orderFulfillmentService;
        this.orderQueryService = orderQueryService;
    }

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return this.orderFulfillmentService.processOrder(requestDtoMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("/user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrderByUserId(@PathVariable int userId) {
        return this.orderQueryService.getProductsByUserId(userId);
    }

}
