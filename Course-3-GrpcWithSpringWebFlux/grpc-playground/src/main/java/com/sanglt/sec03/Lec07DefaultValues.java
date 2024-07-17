package com.sanglt.sec03;

import com.sanglt.models.sec03.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07DefaultValues {

    private static final Logger log = LoggerFactory.getLogger(Lec07DefaultValues.class);

    public static void main(String[] args) {
        // Todo:
        var school = School.newBuilder()

                .build();
    }

}
