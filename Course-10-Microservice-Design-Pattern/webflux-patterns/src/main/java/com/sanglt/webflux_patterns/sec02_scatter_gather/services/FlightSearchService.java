package com.sanglt.webflux_patterns.sec02_scatter_gather.services;

import com.sanglt.webflux_patterns.sec02_scatter_gather.clients.DeltaClient;
import com.sanglt.webflux_patterns.sec02_scatter_gather.clients.FrontierClient;
import com.sanglt.webflux_patterns.sec02_scatter_gather.clients.JetBlueClient;
import com.sanglt.webflux_patterns.sec02_scatter_gather.dtos.FlightResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

    private final DeltaClient deltaClient;
    private final FrontierClient frontierClient;
    private final JetBlueClient jetBlueClient;

    public Flux<FlightResult> getFlights(String from, String to) {
        return Flux.merge(
                        this.deltaClient.getFlights(from, to),
                        this.frontierClient.getFlights(from, to),
                        this.jetBlueClient.getFlights(from, to)
                )
                .take(Duration.ofSeconds(3));
    }



}
