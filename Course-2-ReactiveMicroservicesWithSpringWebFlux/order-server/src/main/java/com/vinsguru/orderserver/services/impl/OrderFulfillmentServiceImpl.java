package com.vinsguru.orderserver.services.impl;

import com.vinsguru.orderserver.clients.ProductClient;
import com.vinsguru.orderserver.clients.UserClient;
import com.vinsguru.orderserver.dtos.requests.PurchaseOrderRequestDto;
import com.vinsguru.orderserver.dtos.requests.RequestContext;
import com.vinsguru.orderserver.dtos.responses.PurchaseOrderResponseDto;
import com.vinsguru.orderserver.repositories.PurchaseOrderRepository;
import com.vinsguru.orderserver.services.OrderFulfillmentService;
import com.vinsguru.orderserver.utils.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    private final ProductClient productClient;

    private final UserClient userClient;

    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderFulfillmentServiceImpl(ProductClient productClient,
                                       UserClient userClient,
                                       PurchaseOrderRepository purchaseOrderRepository) {
        this.productClient = productClient;
        this.userClient = userClient;
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
//                .retry(5)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return this.userClient.authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }

}
