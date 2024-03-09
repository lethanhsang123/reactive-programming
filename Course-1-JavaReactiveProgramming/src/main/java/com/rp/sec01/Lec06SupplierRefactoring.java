package com.rp.sec01;

import com.rp.courseutil.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec06SupplierRefactoring {

    public static void main(String[] args) {

        getName();  //  building pipeline (not execute)
        getName().subscribeOn(Schedulers.boundedElastic()).subscribe(Util.onNext()); // Subscribe and execute pipeline
        getName();  //  building pipeline (not execute)

        Util.sleepSeconds(4);

    }

    private static Mono<String> getName() {
        System.out.println("Entered getName Method");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating name...");
            Util.sleepSeconds(3);
            return Util.faker().name().fullName();
        }).map(String::toUpperCase);
    }
}
