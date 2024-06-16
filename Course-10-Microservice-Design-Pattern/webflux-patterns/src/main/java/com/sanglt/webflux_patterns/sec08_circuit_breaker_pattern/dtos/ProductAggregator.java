package com.sanglt.webflux_patterns.sec08_circuit_breaker_pattern.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductAggregator {

    private Integer id;
    private String category;
    private String description;
    private List<Review> reviews;

}
