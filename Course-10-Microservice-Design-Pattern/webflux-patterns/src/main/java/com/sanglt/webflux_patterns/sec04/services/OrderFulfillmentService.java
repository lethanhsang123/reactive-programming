package com.sanglt.webflux_patterns.sec04.services;

import com.sanglt.webflux_patterns.sec04.clients.ProductClient;
import com.sanglt.webflux_patterns.sec04.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec04.dtos.Product;
import com.sanglt.webflux_patterns.sec04.dtos.Status;
import com.sanglt.webflux_patterns.sec04.utils.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final PaymentOrchestrator paymentOrchestrator;
    private final InventoryOrchestrator inventoryOrchestrator;
    private final ShippingOrchestrator shippingOrchestrator;

    private final ProductClient productClient;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext context) {
        return this.getProduct(context)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(this.paymentOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildInventoryRequest)
                .flatMap(this.inventoryOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildShippingRequest)
                .flatMap(this.shippingOrchestrator::create)
                .doOnNext(ctx -> ctx.setStatus(Status.SUCCESS))
                .doOnError(er -> context.setStatus(Status.FAILED));
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext context) {
        return this.productClient.getProduct(context.getOrderRequest().getProductId())
                .map(Product::getPrice)
                .doOnNext(context::setProductPrice)
                .map(i -> context);
    }


}
