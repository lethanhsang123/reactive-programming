package com.rp.sec06;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec04PublishOn {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(objectFluxSink -> {
                    printThreadName("create");
                    for (int i = 0; i < 4; i++) {
                        objectFluxSink.next(i);
                    }
                    objectFluxSink.complete();
                })
                .doOnNext(o -> printThreadName("next a " + o));

        flux
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("next b " + i))
                .subscribe(o -> printThreadName("sub " + o));

        Util.sleepSeconds(30);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }

}
