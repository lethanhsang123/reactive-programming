package com.sanglt.webflux_patterns.sec05_splitter_pattern.services;

import com.sanglt.webflux_patterns.sec05_splitter_pattern.clients.CarClient;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CarReservationHandler extends ReservationHandler {

    private final CarClient carClient;

    @Override
    protected ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.map(this::toCarRequest)
                .transform(this.carClient::reserve)
                .map(this::toResponse);
    }

    private CarReservationRequest toCarRequest(ReservationItemRequest request) {
        return CarReservationRequest.create(
          request.getCity(),
          request.getFrom(),
          request.getTo(),
          request.getCategory()
        );
    }

    private ReservationItemResponse toResponse(CarReservationResponse response) {
        return ReservationItemResponse.create(
          response.getReservationId(),
                this.getType(),
                response.getCategory(),
                response.getCity(),
                response.getPickup(),
                response.getDrop(),
                response.getPrice()
        );
    }
}
