package com.sanglt.webflux_patterns.sec05_splitter_pattern.services;

import com.sanglt.webflux_patterns.sec05_splitter_pattern.clients.RoomClient;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RoomReservationHandler extends ReservationHandler {

    private final RoomClient roomClient;

    @Override
    protected ReservationType getType() {
        return ReservationType.ROOM;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.map(this::toRoomRequest)
                .transform(this.roomClient::reserve)
                .map(this::toResponse);
    }

    private RoomReservationRequest toRoomRequest(ReservationItemRequest request) {
        return RoomReservationRequest.create(
                request.getCity(),
                request.getFrom(),
                request.getTo(),
                request.getCategory()
        );
    }

    private ReservationItemResponse toResponse(RoomReservationResponse response) {
        return ReservationItemResponse.create(
                response.getReservationId(),
                this.getType(),
                response.getCategory(),
                response.getCity(),
                response.getCheckIn(),
                response.getCheckOut(),
                response.getPrice()
        );
    }
}
