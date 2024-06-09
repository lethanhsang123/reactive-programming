package com.sanglt.webflux_patterns.sec03_orchestrator.services;

import com.sanglt.webflux_patterns.sec03_orchestrator.clients.ProductClient;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.*;
import com.sanglt.webflux_patterns.sec03_orchestrator.utils.DebugUtil;
import com.sanglt.webflux_patterns.sec03_orchestrator.utils.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrchestratorService {

    private final ProductClient productClient;
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancelService orderCancelService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> request) {
        return request
                .map(OrchestrationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(OrchestrationUtil::buildRequestContext)
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                .doOnNext(DebugUtil::print) // Just for debugging
                .map(this::toOrderResponse);
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext context) {
        return this.productClient.getProduct(context.getOrderRequest().getProductId())
                .map(Product::getPrice)
                .doOnNext(context::setProductPrice)
                .map(i -> context);
    }

    private void doOrderPostProcessing(OrchestrationRequestContext context) {
        if (Status.FAILED.equals(context.getStatus())) {
            this.orderCancelService.cancelOrder(context);
        }
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext context) {
        var isSuccess = Status.SUCCESS.equals(context.getStatus());
        var address = isSuccess ? context.getShippingResponse().getAddress() : null;
        var deliveryDate = isSuccess ? context.getShippingResponse().getExpectedDelivery() : null;

        return OrderResponse.create(
                context.getOrderRequest().getUserId(),
                context.getOrderRequest().getProductId(),
                context.getOrderId(),
                context.getStatus(),
                address,
                deliveryDate
        );
    }

}
