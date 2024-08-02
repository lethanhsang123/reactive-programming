package com.sanglt.sec04;

import com.sanglt.models.common.Address;
import com.sanglt.models.common.BodyStyle;
import com.sanglt.models.common.Car;
import com.sanglt.models.sec04.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Import {

    private static final Logger log = LoggerFactory.getLogger(Lec01Import.class);

    public static void main(String[] args) {
        var address = Address.newBuilder().setCity("Atlanta").build();
        var car = Car.newBuilder().setBodyStyle(BodyStyle.COUPE).build();
        var person = Person.newBuilder().setName("Sam").build();

        log.info("Person: {}", person.hasAge());

    }


}
