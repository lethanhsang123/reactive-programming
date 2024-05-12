package com.vinsguru.orderserver.services;

import com.vinsguru.orderserver.dtos.responses.PurchaseOrderResponseDto;
import reactor.core.publisher.Flux;

public interface OrderQueryService {

    Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId);

}
