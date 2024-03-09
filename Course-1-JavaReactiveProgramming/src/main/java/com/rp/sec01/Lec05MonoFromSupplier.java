package com.rp.sec01;

import com.rp.courseutil.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class Lec05MonoFromSupplier {

    public static void main(String[] args) {


        // todo: Mono or publisher should be doing work or that things will execute ONLY IF THE SUBSCRIBER SUBSCRIBES TO IT
        // todo: GetName() executed while test don't subscribed
//        Mono<String> test = Mono.just(getName());

        Supplier<String> supplier = Lec05MonoFromSupplier::getName;
        Mono.fromSupplier(supplier).subscribe(Util.onNext());

        Callable<String> callable = Lec05MonoFromSupplier::getName;
        Mono.fromCallable(callable).subscribe(Util.onNext());

    }

    private static String getName() {
        System.out.println("Generating name...");
        return Util.faker().name().fullName();
    }

}
