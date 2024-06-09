package com.vinsguru.orderserver.utils;

import com.vinsguru.orderserver.dtos.requests.RequestContext;
import com.vinsguru.orderserver.dtos.requests.clients.TransactionRequestDto;
import com.vinsguru.orderserver.dtos.responses.OrderStatus;
import com.vinsguru.orderserver.dtos.responses.PurchaseOrderResponseDto;
import com.vinsguru.orderserver.dtos.responses.clients.TransactionStatus;
import com.vinsguru.orderserver.entities.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext requestContext) {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        transactionRequestDto.setAmount(requestContext.getProductDto().getPrice());
        requestContext.setTransactionRequestDto(transactionRequestDto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDto().getPrice());

        TransactionStatus transactionStatus = requestContext.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(transactionStatus) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);
        return purchaseOrder;
    }

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }

}
