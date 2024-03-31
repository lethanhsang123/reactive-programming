package com.sanglt.redisson.test;

import com.sanglt.redisson.test.config.RedissonConfigurer;
import com.sanglt.redisson.test.dto.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class Lec08LocalCachedMapTest extends BaseTest{

    private RLocalCachedMap<Integer, Student> studentsMap;

    @BeforeAll
    public void setupClient() {
        RedissonConfigurer config = new RedissonConfigurer();
        RedissonClient redissonClient = config.getInstance();

        LocalCachedMapOptions<Integer, Student> mapOptions = LocalCachedMapOptions.<Integer, Student>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE);

        this.studentsMap = redissonClient.getLocalCachedMap(
                "students",
                new TypedJsonJacksonCodec(Integer.class, Student.class),
                mapOptions
        );
    }

    @Test
    public void appServer1() {
        Student student1 = new Student("a", 10, "AA", List.of(1, 2, 3));
        Student student2 = new Student("b", 20, "BB", List.of(4, 5));
        this.studentsMap.put(1, student1);
        this.studentsMap.put(2, student2);

        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.printf(i + " ===> " + studentsMap.get(1))).subscribe();

        sleep(600000);
    }

    @Test
    public void appServer2() {
        Student student1 = new Student("a-updated", 10, "AA", List.of(1, 2, 3));
        this.studentsMap.put(1, student1);
    }

}
