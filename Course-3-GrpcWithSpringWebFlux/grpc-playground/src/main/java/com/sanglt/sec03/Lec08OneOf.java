package com.sanglt.sec03;

import com.sanglt.models.sec03.Credentials;
import com.sanglt.models.sec03.Email;
import com.sanglt.models.sec03.Phone;

public class Lec08OneOf {

    public static void main(String[] args) {
        var email = Email.newBuilder().setAddress("sam@gmail.com").setPassword("admin").build();
        var phone = Phone.newBuilder().setNumber(12345677).setCode(123).build();

//        login(Credentials.newBuilder()
//                .setEmail(email)
//                .build());
//        login(Credentials.newBuilder()
//                .setPhone(phone)
//                .build());
        login(Credentials.newBuilder()
                .setPhone(phone)
                .setEmail(email)
                .build());
        login(Credentials.newBuilder()
                .setEmail(email)
                .setPhone(phone)
                .build());
    }

    private static void login(Credentials credentials) {
        switch (credentials.getLoginTypeCase()) {
            case EMAIL -> System.out.println("email -> " + credentials.getEmail());
            case PHONE -> System.out.println("phone -> " + credentials.getPhone());
        }
    }

}
