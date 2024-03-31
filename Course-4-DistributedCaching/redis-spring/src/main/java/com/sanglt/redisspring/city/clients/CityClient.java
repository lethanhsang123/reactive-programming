package com.sanglt.redisspring.city.clients;

import com.sanglt.redisspring.city.dtos.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityClient {

    private final WebClient webClient;


    public CityClient(@Value("${city.service.uri}") String uri) {
        this.webClient = WebClient.builder()
                .baseUrl(uri)
                .build();
    }

    public Mono<City> getCity(final String zipCode) {
        return this.webClient
                .get()
                .uri("{zipcode}", zipCode)
                .retrieve()
                .bodyToMono(City.class);
    }

    public Flux<City> getAll() {
        return this.webClient
                .get()
                .retrieve()
                .bodyToFlux(City.class);
    }
}
