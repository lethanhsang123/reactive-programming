package com.vinsguru.orderserver.dtos.requests;

import com.vinsguru.orderserver.dtos.requests.clients.TransactionRequestDto;
import com.vinsguru.orderserver.dtos.responses.clients.ProductDto;
import com.vinsguru.orderserver.dtos.responses.clients.TransactionResponseDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestContext {
    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }
}
