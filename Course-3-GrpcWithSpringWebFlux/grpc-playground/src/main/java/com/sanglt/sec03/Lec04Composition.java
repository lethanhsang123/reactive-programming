package com.sanglt.sec03;

import com.sanglt.models.sec03.Address;
import com.sanglt.models.sec03.School;
import com.sanglt.models.sec03.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04Composition {

    private static final Logger log = LoggerFactory.getLogger(Lec04Composition.class);

    public static void main(String[] args) {
        var address = Address.newBuilder()
                .setStreet("Street 1")
                .setCity("City 1")
                .setState("State 1")
                .build();

        var student = Student.newBuilder()
                .setName("Student 1")
                .setAddress(address.toBuilder().setCity("City 2").build())
                .build();

        var school = School.newBuilder()
                .setId(1)
                .setName("School 1")
                .setAddress(address.toBuilder().setCity("City 3").build())
                .build();

        log.info("Address: {}", address);
        log.info("Student: {}", student);
        log.info("School: {}", school);
    }
}
