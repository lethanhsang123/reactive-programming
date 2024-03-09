package com.rp.sec06;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec01Thread {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(objectFluxSink -> {
            printThreadName("create");
            objectFluxSink.next(1);
        }).doOnNext(o -> printThreadName("next " + o));

        Runnable runnable = () ->  flux.subscribe(o -> printThreadName("sub " + o));


        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSeconds(5);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }

}
