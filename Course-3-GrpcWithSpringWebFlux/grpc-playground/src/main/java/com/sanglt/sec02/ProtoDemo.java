package com.sanglt.sec02;

import com.sanglt.models.sec02.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtoDemo {

    private static final Logger log = LoggerFactory.getLogger(ProtoDemo.class);

    public static void main(String[] args) {


        Person person1 = createPerson();
        Person person2 = createPerson();

        // Compare
        log.info("Compare person1 equals person2: {}", person1.equals(person2));
        log.info("Compare person1 == person2: {}", person1 == person2);

        // Mutable: NO

        // Null: NO
//        Person person3 = Person.newBuilder()  NULL Pointer Exception
//                .setName(null)
//                .build();
        Person person3 = Person.newBuilder().clearName().setAge(12).build();
        log.info("Person3 : {}", person3);

        // Create another instances
        Person person4 = person1.toBuilder().clearName().build();
        log.info("Person1: {}", person1);
        log.info("Person4: {}", person4);
        log.info("Test equal {}", person4.equals(person1));
        log.info("Test == {}", person4 == person1);

    }

    private static Person createPerson() {
        return Person.newBuilder()
                .setName("Sam")
                .setAge(12)
                .build();
    }

}
