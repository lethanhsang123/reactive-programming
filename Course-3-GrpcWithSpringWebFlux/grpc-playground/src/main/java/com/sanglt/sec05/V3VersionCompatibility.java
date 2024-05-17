package com.sanglt.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sanglt.models.sec05.v2.Television;
import com.sanglt.models.sec05.v2.Type;
import com.sanglt.sec05.parser.V1Parser;
import com.sanglt.sec05.parser.V2Parser;
import com.sanglt.sec05.parser.V3Parser;

public class V3VersionCompatibility {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("samsung")
                .setModel(2019)
                .setType(Type.UHD)
                .build();

        V1Parser.parse(tv.toByteArray());
        V2Parser.parse(tv.toByteArray());
        V3Parser.parse(tv.toByteArray());
    }

}
