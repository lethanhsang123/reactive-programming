package com.sanglt.redisspring.fib.controllers;

import com.sanglt.redisspring.fib.services.FibService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/fib")
public class FibController {

    private final FibService fibService;

    @GetMapping("/{index}")
    public Mono<Integer> fib(@PathVariable int index) {
        return Mono.fromSupplier(() -> this.fibService.getFib(index));
    }

    @GetMapping("/{index}/clear")
    public Mono<Void> clearCache(@PathVariable int index) {
        return Mono.fromRunnable(() -> this.fibService.clearCache(index));
    }

}
