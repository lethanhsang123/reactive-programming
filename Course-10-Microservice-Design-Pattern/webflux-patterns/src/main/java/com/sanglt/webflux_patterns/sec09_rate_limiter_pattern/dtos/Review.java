package com.sanglt.webflux_patterns.sec09_rate_limiter_pattern.dtos;

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
