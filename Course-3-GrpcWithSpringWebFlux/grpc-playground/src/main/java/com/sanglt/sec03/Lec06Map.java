package com.sanglt.sec03;

import com.sanglt.models.common.BodyStyle;
import com.sanglt.models.common.Car;
import com.sanglt.models.common.Dealer;

public class Lec06Map {

    public static void main(String[] args) {
        var car1 = Car.newBuilder()
                .setMake("honda")
                .setModel("civic")
                .setYear(2000)
                .setBodyStyle(BodyStyle.COUP)
                .build();

        var car2 = Car.newBuilder()
                .setMake("honda")
                .setModel("accord")
                .setYear(2002)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();

        var dealer = Dealer.newBuilder()
                .putInventory(1, car1)
                .putInventory(2, car2)
                .build();

        System.out.println("Result: " + dealer);
        System.out.println("2002 ? " + dealer.containsInventory(2002));
        System.out.println("2003 ? " + dealer.containsInventory(2003));
//        System.out.println("2002 model : " + dealer.getInventoryOrThrow(2002).getModel());
        System.out.println("2002 model : " + dealer.getInventoryOrDefault(2002, car1.toBuilder().setMake("aaaa").build()).getBodyStyle());
    }

}
