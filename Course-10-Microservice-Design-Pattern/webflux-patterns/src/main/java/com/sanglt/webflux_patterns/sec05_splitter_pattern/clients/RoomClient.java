package com.sanglt.webflux_patterns.sec05_splitter_pattern.clients;

import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.CarReservationRequest;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.CarReservationResponse;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.RoomReservationRequest;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.RoomReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomClient {

    private final WebClient client;

    public RoomClient(@Value("${sec05.room.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> flux) {
        return this.client
                .post()
                .body(flux, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }

}
