package com.sanglt.webflux_patterns.sec03_orchestrator.utils;

import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.InventoryRequest;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.PaymentRequest;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.ShippingRequest;

public class OrchestrationUtil {

    public static void buildRequestContext(OrchestrationRequestContext context) {
        buildPaymentRequest(context);
        buildInventoryRequest(context);
        buildShippingRequest(context);
    }

    private static void buildPaymentRequest(OrchestrationRequestContext context) {
        var paymentRequest = PaymentRequest.create(
                context.getOrderRequest().getUserId(),
                context.getProductPrice() * context.getOrderRequest().getQuantity(),
                context.getOrderId()
        );
        context.setPaymentRequest(paymentRequest);
    }

    private static void buildInventoryRequest(OrchestrationRequestContext context) {
        var inventoryRequest = InventoryRequest.create(
                context.getOrderId(),
                context.getOrderRequest().getProductId(),
                context.getOrderRequest().getQuantity()
        );
        context.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(OrchestrationRequestContext context) {
        var shippingRequest = ShippingRequest.create(
                context.getOrderRequest().getQuantity(),
                context.getOrderRequest().getUserId(),
                context.getOrderId()
        );
        context.setShippingRequest(shippingRequest);
    }
}
