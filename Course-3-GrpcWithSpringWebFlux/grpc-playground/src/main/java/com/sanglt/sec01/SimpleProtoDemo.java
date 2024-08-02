package com.sanglt.sec01;

import com.sanglt.models.sec01.PersonOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProtoDemo {

    private static final Logger log = LoggerFactory.getLogger(SimpleProtoDemo.class);

    public static void main(String[] args) {

        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder()
                .setName("Name ne")
                .setAge(120)
                .build();

        log.info("Test sec01: {}", person);
    }

}
