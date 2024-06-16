package com.sanglt.webflux_patterns.sec08_circuit_breaker_pattern.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {

    private Integer id;
    private String category;
    private String description;
    private Integer price;

}
