package com.sanglt.sec03;

import com.sanglt.models.sec03.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Lec02Serialization {

    private static final Path PATH = Path.of("person.out");

    public static void main(String[] args) throws IOException {
        var person = Person.newBuilder()
                .setLastName("sam")
                .setAge(12)
                .setEmail("sam@gmail.com")
                .setEmployed(Boolean.TRUE)
                .setSalary(100.220)
                .setBankAccountNumber(122678L)
                .setBalance(-10000)
                .build();

        serialize(person);
        System.out.println(deserialize());
        System.out.println("equals: " + person.equals(deserialize()));
        System.out.println("bytes length: " + person.toByteArray().length);
    }

    public static void serialize(Person person) throws IOException {
        person.writeTo(Files.newOutputStream(PATH));
    }

    public static Person deserialize() throws IOException {
        return Person.parseFrom(Files.newInputStream(PATH));
    }

}
