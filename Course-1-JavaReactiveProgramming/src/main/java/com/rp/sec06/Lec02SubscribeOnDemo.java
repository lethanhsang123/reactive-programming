package com.rp.sec06;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOnDemo {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(objectFluxSink -> {
            printThreadName("create");
            objectFluxSink.next(1);
        })
                .doFirst(() -> printThreadName("doFirst1-2"))
                .subscribeOn(Schedulers.newParallel("vins"))
                .doFirst(() -> printThreadName("doFirst1-1"))
                .doOnNext(o -> printThreadName("next " + o));

        Runnable runnable = () ->  flux
                .doFirst(() -> printThreadName("doFirst2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("doFirst1"))
                .log()
                .subscribe(o -> printThreadName("sub " + o));

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSeconds(20);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }

}
