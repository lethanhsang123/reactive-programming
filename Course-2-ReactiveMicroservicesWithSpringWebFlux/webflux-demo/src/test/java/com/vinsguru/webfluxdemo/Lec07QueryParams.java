package com.vinsguru.webfluxdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class Lec07QueryParams extends BaseTest{

    @Autowired
    private WebClient webClient;

    private final String URI_QUERY = "http://localhost:8080/jobs/search?count={count}&page={page}";


    private void queryParamsTest() {

        Map<String, Integer> map = Map.of(
                "count", 10,
                "page", 20
        );
        URI uri = UriComponentsBuilder.fromUriString(this.URI_QUERY).build(map);

        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

}
