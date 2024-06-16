package com.sanglt.webflux_patterns.sec04.services;

import com.sanglt.webflux_patterns.sec04.clients.ProductClient;
import com.sanglt.webflux_patterns.sec04.dtos.*;
import com.sanglt.webflux_patterns.sec04.utils.DebugUtil;
import com.sanglt.webflux_patterns.sec04.utils.OrchestrationUtil;
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
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                .doOnNext(DebugUtil::print) // Just for debugging
                .map(this::toOrderResponse);
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
