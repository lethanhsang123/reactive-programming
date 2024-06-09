package com.sanglt.sec04;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import com.sanglt.models.sec04.Sample;

import java.time.Instant;

public class Lec02WellKnownTypes {

    public static void main(String[] args) {
        var sample = Sample.newBuilder()
                .setAge(Int32Value.of(12))
                .setLoginTime(Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()).build())
                .build();
        System.out.println("Time: " + Instant.ofEpochSecond(sample.getLoginTime().getSeconds()));
    }

}
