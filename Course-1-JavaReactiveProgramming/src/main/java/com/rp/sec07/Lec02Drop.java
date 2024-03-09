package com.rp.sec07;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class Lec02Drop {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        System.setProperty("reactor.bufferSize.small", "16");
        Flux.create(fluxSink -> {
                    for (int i = 1; i < 501; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed : " + i);
                        Util.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
                .onBackpressureDrop(list::add)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(o -> Util.sleepMillis(10))
                .subscribe(Util.subscriber());


        Util.sleepSeconds(10);
        System.out.println(list);

    }

}
