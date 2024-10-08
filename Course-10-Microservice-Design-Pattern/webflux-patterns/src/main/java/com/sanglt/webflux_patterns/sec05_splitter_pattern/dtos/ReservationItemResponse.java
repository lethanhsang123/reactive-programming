package com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ReservationItemResponse {

    private UUID itemId;
    private ReservationType type;
    private String category;
    private String city;
    private LocalDate from;
    private LocalDate to;
    private Integer price;
}
