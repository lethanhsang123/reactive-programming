package com.vinsguru.webfluxdemo.controller;

import com.vinsguru.webfluxdemo.dto.Response;
import com.vinsguru.webfluxdemo.exception.InputValidationException;
import com.vinsguru.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math")
public class ReactiveValidationController {

    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("/square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) throw new InputValidationException(input);
        return this.reactiveMathService.findSquare(input);
    }

    @GetMapping("/square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, responseSynchronousSink) -> {
                    if (integer < 10 || integer > 20) responseSynchronousSink.error(new InputValidationException(integer));
                    else responseSynchronousSink.next(integer);
                })
                .cast(Integer.class)
                .flatMap(integer -> this.reactiveMathService.findSquare(integer));
    }

}
