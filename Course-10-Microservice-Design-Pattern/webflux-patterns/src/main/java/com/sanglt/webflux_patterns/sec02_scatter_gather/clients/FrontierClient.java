package com.sanglt.webflux_patterns.sec02_scatter_gather.clients;

import com.sanglt.webflux_patterns.sec02_scatter_gather.dtos.FlightResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FrontierClient {
    private final WebClient client;

    public FrontierClient(@Value("${sec02.frontier.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return this.client
                .post()
                .bodyValue(FrontierRequest.create(from, to))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(ex -> Mono.empty());
    }

    @Data
    @ToString
    @AllArgsConstructor(staticName = "create")
    private static class FrontierRequest {
        private String from;
        private String to;
    }

}
