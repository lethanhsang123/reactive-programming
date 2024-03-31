package com.sanglt.redisperformance.services;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.IntegerCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductVisitService {

    private final RedissonReactiveClient client;
    private final Sinks.Many<Integer> sink;

    public ProductVisitService(RedissonReactiveClient client) {
        this.client = client;
        this.sink = Sinks.many().unicast().onBackpressureBuffer();
    }

    @PostConstruct
    private void init() {
        this.sink
                .asFlux()
                .buffer(Duration.ofSeconds(3))
                .map(l -> l.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                ))
                .flatMap(this::updateBatch).subscribe();
    }

    public void addVisit(int productId) {
        this.sink.tryEmitNext(productId);
    }

    private Mono<Void> updateBatch(Map<Integer, Long> map) {
        RBatchReactive batch = this.client.createBatch(BatchOptions.defaults());
        String format = DateTimeFormatter.ofPattern("YYYYMMdd").format(LocalDate.now());
        RScoredSortedSetReactive<Integer> set = batch.getScoredSortedSet("product:visit:" + format, IntegerCodec.INSTANCE);

        return Flux.fromIterable(map.entrySet())
                .map(e -> set.addScore(e.getKey(), e.getKey()))
                .then(batch.execute())
                .then();
    }

}
