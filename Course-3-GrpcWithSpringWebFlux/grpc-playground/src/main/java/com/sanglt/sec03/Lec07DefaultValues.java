package com.sanglt.sec03;

import com.sanglt.models.common.Address;
import com.sanglt.models.common.Car;
import com.sanglt.models.common.Dealer;
import com.sanglt.models.common.School;
import com.sanglt.models.sec03.Library;

public class Lec07DefaultValues {

    public static void main(String[] args) {
        var school = School.newBuilder()
                .build();

        System.out.println("Id: " + school.getId());
        System.out.println("Name: " + school.getName());
        System.out.println("Address: " + school.getAddress().getCity());

        System.out.println("IS default: " + school.getAddress().equals(Address.getDefaultInstance()));

        System.out.println("Has address? " + school.hasAddress());

        var lib = Library.newBuilder().build();
        System.out.println("Lib: " + lib.getBooksList());

        var dealer = Dealer.newBuilder().build();
        System.out.println("Dealer: " + dealer.getInventoryMap());

        var car = Car.newBuilder().build();
        System.out.println("Car: " + car.getBodyStyle());

    }

}
