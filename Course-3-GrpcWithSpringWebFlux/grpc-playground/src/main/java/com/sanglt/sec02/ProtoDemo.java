package com.sanglt.sec02;

import com.sanglt.models.sec02.Person;

public class ProtoDemo {

    public static void main(String[] args) {

        // create person 1
        var person1 = createPerson();



    }


    private static Person createPerson() {
        return Person.newBuilder()
                .setName("sam")
                .setAge(12)
                .build();
    }
}
