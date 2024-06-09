package com.sanglt.webflux_patterns.sec02_scatter_gather.controllers;

import com.sanglt.webflux_patterns.sec02_scatter_gather.dtos.FlightResult;
import com.sanglt.webflux_patterns.sec02_scatter_gather.services.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/sec02")
@RequiredArgsConstructor
public class FlightController {

    private final FlightSearchService flightSearchService;

    @GetMapping(value = "/flights/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable String from, @PathVariable String to) {
        return flightSearchService.getFlights(from, to);
    }

}
