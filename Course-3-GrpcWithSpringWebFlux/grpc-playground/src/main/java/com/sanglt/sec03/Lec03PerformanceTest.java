package com.sanglt.sec03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sanglt.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec03PerformanceTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03PerformanceTest.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static void main(String[] args) {
        var protoPerson = Person.newBuilder()
                .setLastName("Sam")
                .setAge(12)
                .setEmail("sam@gmail.com")
                .setEmployed(true)
                .setSalary(1000.2345)
                .setBankAccountNumber(123456789)
                .setBalance(-123456)
                .build();

        var jsonPerson = new JsonPerson("Sam",
                12,
                "sam@gmail.com",
                true,
                1000.2345,
                123456789,
                -123456);

//        for (int i = 0; i < 5; i++) {
//            runTest("Json", () -> json(jsonPerson));
//            runTest("Proto", () -> proto(protoPerson));
//        }

        json(jsonPerson);
        proto(protoPerson);
    }

    private static void proto(Person person) {
        try {
            var bytes = person.toByteArray();
            log.info("Proto bytes length: {}", bytes.length);
            Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private static void json(JsonPerson person) {
        try {
            var bytes = MAPPER.writeValueAsBytes(person);
            log.info("Json bytes length: {}", bytes.length);
            MAPPER.readValue(bytes, JsonPerson.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void runTest(String testName, Runnable runnable) {
        var start = System.currentTimeMillis();
        for (int i = 0; i < 8_000_000; i++) {
            runnable.run();
        }
        var end = System.currentTimeMillis();
        log.info("Time taken for {} = {} ms", testName, end - start );
    }


}
