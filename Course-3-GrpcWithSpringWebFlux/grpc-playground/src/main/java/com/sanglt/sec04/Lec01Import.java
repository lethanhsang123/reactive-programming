package com.sanglt.sec04;

import com.sanglt.models.common.Address;
import com.sanglt.models.common.BodyStyle;
import com.sanglt.models.common.Car;
import com.sanglt.models.sec04.Person;

public class Lec01Import {

    public static void main(String[] args) {
        var address = Address.newBuilder()
                .setCity("atlanta")
                .build();
        var car = Car.newBuilder().setBodyStyle(BodyStyle.COUP).build();
        var person = Person.newBuilder()
                .setName("sam")
//                .setAge(12)
                .setCar(car)
                .setAddress(address)
                .build();
        System.out.println("Person: " + person.hasAge());
    }

}
