package com.sanglt.common;

import com.sanglt.sec06.BankService;
import com.sanglt.sec06.TransferService;

public class Demo {

    public static void main(String[] args) {

        GrpcServer.create(new BankService(), new TransferService())
                .start()
                .await();

    }

}
