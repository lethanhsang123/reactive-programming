package com.sanglt.sec04;

import com.sanglt.models.sec03.Address;
import com.sanglt.models.sec03.BodyStyle;
import com.sanglt.models.sec03.Car;
import com.sanglt.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Import {

    private static final Logger log = LoggerFactory.getLogger(Lec01Import.class);

    public static void main(String[] args) {
        var address = Address.newBuilder().setCity("Atlanta").build();
        var car = Car.newBuilder().setBodyStyle(BodyStyle.COUPE).build();
        var person = Person.newBuilder().setLastName("Sam").setAge(12).build();

    }


}
