package com.sanglt.webflux_patterns.sec05_splitter_pattern.services;

import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationItemRequest;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationItemResponse;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationResponse;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final Map<ReservationType, ReservationHandler> map;

    public ReservationService(List<ReservationHandler> list) {
        this.map = list.stream().collect(Collectors.toMap(
                ReservationHandler::getType,
                Function.identity()
        ));
    }

    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.groupBy(ReservationItemRequest::getType)   // splitter
                .flatMap(this::aggregator)
                .collectList()
                .map(this::toResponse);
    }

    private Flux<ReservationItemResponse> aggregator(GroupedFlux<ReservationType, ReservationItemRequest> groupedFlux) {
        var key = groupedFlux.key();
        var handler = map.get(key);
        return handler.reserve(groupedFlux);
    }

    private ReservationResponse toResponse(List<ReservationItemResponse> list) {
        return ReservationResponse.create(
                UUID.randomUUID(),
                list.stream().mapToInt(ReservationItemResponse::getPrice).sum(),
                list
        );
    }

}
