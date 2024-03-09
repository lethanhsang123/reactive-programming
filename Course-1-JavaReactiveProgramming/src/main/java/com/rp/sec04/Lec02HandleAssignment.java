package com.rp.sec04;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class Lec02HandleAssignment {

    public static void main(String[] args) {

        Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.faker().country().name());})
                .map(Objects::toString)
                .handle((s, synchronousSink) -> {
                    synchronousSink.next(s);
                    if (s.equalsIgnoreCase("canada")) synchronousSink.complete();
                })
                .subscribe(Util.subscriber("Test"));

    }

}
