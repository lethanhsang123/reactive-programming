package com.vinsguru.orderserver.dtos.responses.clients;

import lombok.Data;

@Data
public class TransactionResponseDto {
    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}
