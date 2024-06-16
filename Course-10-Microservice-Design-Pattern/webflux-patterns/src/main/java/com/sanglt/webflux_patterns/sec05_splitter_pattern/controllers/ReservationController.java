package com.sanglt.webflux_patterns.sec05_splitter_pattern.controllers;

import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationItemRequest;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.dtos.ReservationResponse;
import com.sanglt.webflux_patterns.sec05_splitter_pattern.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec05")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> flux) {
        return this.reservationService.reserve(flux);
    }

}
