package com.sanglt.webflux_patterns.sec02_scatter_gather.clients;

import com.sanglt.webflux_patterns.sec02_scatter_gather.dtos.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JetBlueClient {

    private final WebClient client;

    private static final String JETBLUE = "JETBLUE";

    public JetBlueClient(@Value("${sec02.jetblue.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return this.client
                .get()
                .uri("{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .doOnNext(fr -> this.normalizeResponse(fr, from, to))
                .onErrorResume(ex -> Mono.empty());
    }

    private void normalizeResponse(FlightResult flightResult, String from, String to) {
        flightResult.setFrom(from);
        flightResult.setTo(to);
        flightResult.setAirline(JETBLUE);
    }

}
