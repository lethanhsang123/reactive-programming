package com.vinsguru.orderserver.dtos.requests;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderRequestDto {

    private Integer userId;
    private String productId;

}
