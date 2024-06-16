package com.sanglt.webflux_patterns.sec05_splitter_pattern.services;

import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationItemRequest;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationItemResponse;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationType;
import reactor.core.publisher.Flux;

public abstract class ReservationHandler {
     protected abstract ReservationType getType();
     protected abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux);
}
