package com.sanglt.redisson.test;

import com.sanglt.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class Lec06MapTest extends BaseTest{

    @Test
    public void mapTest() {
        RMapReactive<String, String> map = this.client.getMap("user:1", StringCodec.INSTANCE);
        Mono<String> name = map.put("name", "sang");
        Mono<String> age = map.put("age", "10");
        Mono<String> city = map.put("city", "HP");
        StepVerifier.create(name.concatWith(age).concatWith(city).then()).verifyComplete();
    }
    @Test
    public void mapTest2() {
        RMapReactive<String, String> map = this.client.getMap("user:1", StringCodec.INSTANCE);
        Map<String, String> javaMap = Map.of(
          "name", "sang",
          "age", "10",
          "city", "HP"
        );
        StepVerifier.create(map.putAll(javaMap).then()).verifyComplete();
    }

    @Test
    public void mapTest3() {
        // Map<Integer, Student>
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapReactive<Integer, Student> map = this.client.getMap("users", codec);

        Student student1 = new Student("a", 10, "AA", List.of(1, 2, 3));
        Student student2 = new Student("b", 20, "BB", List.of(4, 5));
        Mono<Student> mono1 = map.put(1, student1);
        Mono<Student> mono2 = map.put(2, student2);

        StepVerifier.create(mono1.concatWith(mono2).then()).verifyComplete();
    }

}
