package com.vinsguru.orderserver.services;

import com.vinsguru.orderserver.dtos.requests.PurchaseOrderRequestDto;
import com.vinsguru.orderserver.dtos.responses.PurchaseOrderResponseDto;
import reactor.core.publisher.Mono;

public interface OrderFulfillmentService {
    Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> request);
}
