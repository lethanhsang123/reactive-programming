package com.rp.sec05;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class Lec01ColdPublisher {

    public static void main(String[] args) {

        Flux<String> movieStream = Flux.fromStream(Lec01ColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2));

        movieStream.subscribe(Util.subscriber("sam"));

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        movieStream.subscribe(Util.subscriber("mike"));

    }

    private static Stream<String> getMovie() {
        System.out.println("Got the movie streaming req");
        return Stream.of(
                "Scene 1",
                "Scene 2",
                "Scene 3",
                "Scene 4",
                "Scene 5",
                "Scene 5",
                "Scene 7"
        );
    }

}
