package com.sanglt.webflux_patterns.sec01_gateway_aggregator.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductResponse {

    private Integer id;
    private String category;
    private String description;
    private Integer price;

}
