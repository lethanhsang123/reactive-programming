package com.rp.sec01;

import reactor.core.publisher.Mono;

public class Lec02MonoJust {

    public static void main(String[] args) {

        // Publisher
        Mono<Integer> just = Mono.just(1);

        System.out.println(just);

        // The number one rule in reactive programming is nothing happens until you subscribe
        // Subscribe
        just.subscribe(System.out::println);



    }


}
