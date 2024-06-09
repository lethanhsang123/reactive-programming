package com.sanglt.sec01;

import com.sanglt.models.sec01.PersonOuterClass;

public class SimpleProtoDemo {

//    private static final Logger logger = LoggerFactory.getLogger(SimpleProtoDemo.class);

    public static void main(String[] args) {
        var person = PersonOuterClass.Person.newBuilder()
                .setName("sam")
                .setAge(12)
                .build();
        System.out.println(person);
    }

}
