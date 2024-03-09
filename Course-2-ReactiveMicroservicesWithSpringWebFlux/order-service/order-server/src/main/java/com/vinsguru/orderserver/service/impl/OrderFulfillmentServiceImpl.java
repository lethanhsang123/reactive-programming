package com.vinsguru.orderserver.service.impl;

import com.vinsguru.orderserver.client.ProductClient;
import com.vinsguru.orderserver.client.UserTransactionClient;
import com.vinsguru.orderserver.dto.request.PurchaseOrderRequestDto;
import com.vinsguru.orderserver.dto.request.RequestContext;
import com.vinsguru.orderserver.dto.response.PurchaseOrderResponseDto;
import com.vinsguru.orderserver.repository.PurchaseOrderRepository;
import com.vinsguru.orderserver.service.OrderFulfillmentService;
import com.vinsguru.orderserver.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    private final ProductClient productClient;

    private final UserTransactionClient userTransactionClient;

    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderFulfillmentServiceImpl(ProductClient productClient,
                                       UserTransactionClient userTransactionClient,
                                       PurchaseOrderRepository purchaseOrderRepository) {
        this.productClient = productClient;
        this.userTransactionClient = userTransactionClient;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> request) {
        return request
                .map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
//                .publishOn(Schedulers.boundedElastic())
                .map(this.purchaseOrderRepository::save)    // blocking (JPA not ODBC)
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext requestContext) {
        return this.productClient
                .getProductById(requestContext.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(requestContext::setProductDto)
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return this.userTransactionClient.authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }

}
