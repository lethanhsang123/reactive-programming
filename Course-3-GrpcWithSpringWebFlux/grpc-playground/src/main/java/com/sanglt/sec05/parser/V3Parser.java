package com.sanglt.sec05.parser;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sanglt.models.sec05.v3.Television;

public class V3Parser {

    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);
        System.out.println("Brand: " + tv.getBrand());
        System.out.println("Type: " + tv.getType());
    }

}
