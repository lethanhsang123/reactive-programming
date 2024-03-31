package com.sanglt.redisson.test;

import com.sanglt.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

public class Lec02KeyValueObjectTest extends BaseTest{

    @Test
    public void keyValueObjectTest() {
        Student student = new Student("sang", 10, "HP", Arrays.asList(1, 2, 3, 4));
        RBucketReactive<Student> bucket = this.client.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));
        Mono<Void> set = bucket.set(student);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(set.concatWith(get)).verifyComplete();
    }


}
