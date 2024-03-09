package com.rp.sec02;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {


    public static void main(String[] args) {
//        List<Integer> list = List.of("1", "2", "3", "4");

        Flux.range(1, 10)
                .count()
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }

}
