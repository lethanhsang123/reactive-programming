package com.vinsguru.orderserver.service;

import com.vinsguru.orderserver.dto.request.PurchaseOrderRequestDto;
import com.vinsguru.orderserver.dto.response.PurchaseOrderResponseDto;
import reactor.core.publisher.Mono;

public interface OrderFulfillmentService {
    Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> request);
}
