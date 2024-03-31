package com.sanglt.redisson.test;

import com.sanglt.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec07MapCacheTest extends BaseTest{

    @Test
    public void mapCacheTest() {
        // Map<Integer, Student>
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapCacheReactive<Integer, Student> mapCache = this.client.getMapCache("users:cache", codec);

        Student student1 = new Student("a", 10, "AA", List.of(1, 2, 3));
        Student student2 = new Student("b", 20, "BB", List.of(4, 5));
        Mono<Student> mono1 = mapCache.put(1, student1, 5, TimeUnit.SECONDS);
        Mono<Student> mono2 = mapCache.put(2, student2, 10, TimeUnit.SECONDS);

        StepVerifier.create(mono1.then(mono2).then()).verifyComplete();
    }

}
