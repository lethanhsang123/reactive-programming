package com.vinsguru.orderserver.service;

import com.vinsguru.orderserver.dto.response.PurchaseOrderResponseDto;
import reactor.core.publisher.Flux;

public interface OrderQueryService {

    Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId);

}
