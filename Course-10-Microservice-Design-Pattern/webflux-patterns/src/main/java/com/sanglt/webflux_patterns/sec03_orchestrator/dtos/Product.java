package com.sanglt.webflux_patterns.sec03_orchestrator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Product {

    private Integer id;
    private String category;
    private String description;
    private Integer price;

}
