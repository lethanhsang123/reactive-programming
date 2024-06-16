package com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ReservationResponse {
    private UUID reservationId;
    private Integer price;
    private List<ReservationItemResponse> items;
}
