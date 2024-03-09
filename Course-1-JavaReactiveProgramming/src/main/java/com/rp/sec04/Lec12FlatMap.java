package com.rp.sec04;

import com.rp.courseutil.Util;
import com.rp.sec04.helper.OrderService;
import com.rp.sec04.helper.UserService;

public class Lec12FlatMap {

    public static void main(String[] args) {

//        UserService.getUser().flatMap(user -> OrderService.getOrder(user.getUserId()))
//                .subscribe(Util.subscriber());

        UserService.getUsers()
                .flatMap(user -> OrderService.getOrders(user.getUserId()))// Mono/Flux
                .subscribe(Util.subscriber());

        UserService.getUsers()
                .concatMap(user -> OrderService.getOrders(user.getUserId()))// Mono/Flux
                .subscribe(Util.subscriber());



    }

}
