package com.sanglt.webflux_patterns.sec10_bulk_head_pattern.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Review {

    private Integer id;
    private String user;
    private Integer rating;
    private String comment;

}
