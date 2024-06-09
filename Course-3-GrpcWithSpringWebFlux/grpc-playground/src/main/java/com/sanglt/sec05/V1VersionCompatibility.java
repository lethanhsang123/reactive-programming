package com.sanglt.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sanglt.models.sec05.v1.Television;
import com.sanglt.sec05.parser.V1Parser;

public class V1VersionCompatibility {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("samsung")
                .setYear(2019)
                .build();
        V1Parser.parse(tv.toByteArray());
    }

}
