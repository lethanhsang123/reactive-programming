package com.sanglt.webflux_patterns.sec04.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentResponse {

    private UUID paymentId;
    private Integer userId;
    private String name;
    private Integer balance;
    private Status status;
}
