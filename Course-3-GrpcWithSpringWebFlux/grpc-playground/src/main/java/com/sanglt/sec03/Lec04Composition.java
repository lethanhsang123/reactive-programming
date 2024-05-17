package com.sanglt.sec03;

import com.sanglt.models.common.Address;
import com.sanglt.models.common.School;
import com.sanglt.models.common.Student;

public class Lec04Composition {

    public static void main(String[] args) {

        var address = Address.newBuilder()
                .setStreet("123 main st")
                .setCity("atlanta")
                .setState("GA")
                .build();

        var student = Student.newBuilder()
                .setName("sam")
                .setAddress(address)
                .build();

        var school = School.newBuilder()
                .setId(1)
                .setName("high school")
                .setAddress(address.toBuilder().setStreet("234 main st").build())
                .build();
        System.out.println("Student: " + student.getAddress().getStreet());
        System.out.println("School: " + school.getAddress().getStreet());
    }

}
