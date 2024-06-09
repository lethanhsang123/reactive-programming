package com.sanglt.webflux_patterns.sec03_orchestrator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryRequest {
    private UUID orderId;
    private Integer productId;
    private Integer quantity;
}
