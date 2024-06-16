package com.sanglt.webflux_patterns.sec04.utils;

import com.sanglt.webflux_patterns.sec04.dtos.InventoryRequest;
import com.sanglt.webflux_patterns.sec04.dtos.OrchestrationRequestContext;
import com.sanglt.webflux_patterns.sec04.dtos.PaymentRequest;
import com.sanglt.webflux_patterns.sec04.dtos.ShippingRequest;

public class OrchestrationUtil {

    public static void buildPaymentRequest(OrchestrationRequestContext context) {
        var paymentRequest = PaymentRequest.create(
                context.getOrderRequest().getUserId(),
                context.getProductPrice() * context.getOrderRequest().getQuantity(),
                context.getOrderId()
        );
        context.setPaymentRequest(paymentRequest);
    }

    public static void buildInventoryRequest(OrchestrationRequestContext context) {
        var inventoryRequest = InventoryRequest.create(
                context.getPaymentResponse().getPaymentId(),
                context.getOrderRequest().getProductId(),
                context.getOrderRequest().getQuantity()
        );
        context.setInventoryRequest(inventoryRequest);
    }

    public static void buildShippingRequest(OrchestrationRequestContext context) {
        var shippingRequest = ShippingRequest.create(
                context.getOrderRequest().getQuantity(),
                context.getOrderRequest().getUserId(),
                context.getPaymentResponse().getPaymentId()
        );
        context.setShippingRequest(shippingRequest);
    }
}
