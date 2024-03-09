package com.vinsguru.orderserver.service.impl;

import com.vinsguru.orderserver.dto.response.PurchaseOrderResponseDto;
import com.vinsguru.orderserver.repository.PurchaseOrderRepository;
import com.vinsguru.orderserver.service.OrderQueryService;
import com.vinsguru.orderserver.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderQueryServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
        return Flux.fromStream(() -> this.purchaseOrderRepository.findByUserId(userId).stream())// blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
