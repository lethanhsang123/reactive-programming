package com.vinsguru.orderserver.dtos.requests.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionRequestDto {
    @JsonProperty(value = "user_id")
    private Integer userId;
    private Integer amount;
}
