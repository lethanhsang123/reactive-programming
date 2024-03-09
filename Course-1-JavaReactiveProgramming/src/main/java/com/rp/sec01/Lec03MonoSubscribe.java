package com.rp.sec01;

import com.rp.courseutil.Util;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {

    public static void main(String[] args) {

        // Publisher
        Mono<Integer> test = Mono.just("Test")
                .map(String::length)
                .map(l -> l / 1);

        // 1
        // test.subscribe();

        // 2
        test.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );

    }

}
