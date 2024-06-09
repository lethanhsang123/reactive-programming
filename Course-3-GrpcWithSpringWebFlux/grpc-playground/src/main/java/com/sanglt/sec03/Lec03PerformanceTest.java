package com.sanglt.sec03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sanglt.models.sec03.Person;

public class Lec03PerformanceTest {

    private static final ObjectMapper ob = new ObjectMapper();

    public static void main(String[] args) {
        var person = Person.newBuilder()
                .setLastName("sam")
                .setAge(12)
                .setEmail("sam@gmail.com")
                .setEmployed(Boolean.TRUE)
                .setSalary(100.220)
                .setBankAccountNumber(122678L)
                .setBalance(-10000)
                .build();
        var jsonPerson = new JsonPerson("sam",
                12,
                "sam@gmail.com",
                true,
                1000.2345,
                123456789L,
                -1000);

        for (int i = 0; i < 5; i++) {
            runTest("json", () -> json(jsonPerson));
            runTest("proto", () -> proto(person));
        }
    }

    private static void proto(Person person) {
        try {
            var bytes = person.toByteArray();
            System.out.println("Proto bytes length: " + bytes.length);
            Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private static void json(JsonPerson person) {
        try {
            var bytes = ob.writeValueAsBytes(person);
            System.out.println("Json bytes length: " + bytes.length);
            ob.readValue(bytes, JsonPerson.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void runTest(String testName, Runnable runnable) {
        var start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            runnable.run();
        }
        var end = System.currentTimeMillis();
        System.out.println("Time taken for " + testName + " - " + (end - start) +" ms");
    }



}
