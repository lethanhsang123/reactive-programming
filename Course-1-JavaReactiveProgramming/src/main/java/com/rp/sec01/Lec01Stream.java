package com.rp.sec01;

import java.util.stream.Stream;

public class Lec01Stream {

    public static void main(String[] args) {

        Stream<Integer> stream = Stream.of(1, 2, 3, 5).map(item -> {
            try {
                Thread.sleep(1000); // current thread sleep 1s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return item * 2;
        });

        stream.forEach(System.out::println);

    }

}
