package com.sanglt.sec05.parser;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sanglt.models.sec05.v1.Television;

public class V1Parser {

    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);
        System.out.println("brand: " + tv.getBrand());
        System.out.println("year: " + tv.getYear());
        System.out.println("Unknown properties: " + tv.getUnknownFields());
    }

}
