package com.vinsguru.orderserver.dtos.responses.clients;

import lombok.Data;

@Data
public class ProductDto {
    private String id;
    private String description;
    private Integer price;
}
