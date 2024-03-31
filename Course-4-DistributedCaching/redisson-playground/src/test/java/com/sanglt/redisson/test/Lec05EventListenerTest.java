package com.sanglt.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

public class Lec05EventListenerTest extends BaseTest{

    @Test
    public void expiredEventTest() {
        RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("same", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        Mono<Void> event = bucket.addListener((ExpiredObjectListener) s -> System.out.println("Expired : " + s)).then();

        StepVerifier.create(set.concatWith(get).concatWith(event)).verifyComplete();

        sleep(11000);
    }

    @Test
    public void deletedEventTest() {
        RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("same", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        Mono<Void> event = bucket.addListener((DeletedObjectListener) s -> System.out.println("Deleted : " + s)).then();

        StepVerifier.create(set.concatWith(get).concatWith(event)).verifyComplete();

        sleep(60000);
    }

}
