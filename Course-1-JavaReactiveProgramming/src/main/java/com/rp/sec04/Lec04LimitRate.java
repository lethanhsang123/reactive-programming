package com.rp.sec04;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec04LimitRate {

    public static void main(String[] args) {

        Flux.range(1, 200)
                .log()
                .limitRate(100, 90) // 90%
                .subscribe(Util.subscriber());

    }

}
