package com.sanglt.sec06;

import com.sanglt.models.sec06.AccountBalance;
import com.sanglt.models.sec06.BalanceCheckRequest;
import com.sanglt.models.sec06.BankServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class GrpcClient {

    private static final Logger log = LoggerFactory.getLogger(GrpcClient.class);

    public static void main(String[] args) throws InterruptedException {

        var channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        // Synchronous
        /*
        var stub = BankServiceGrpc.newBlockingStub(channel);

        var balance = stub.getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(2).build());

        log.info("{}", balance);
         */

        // Asynchronous
        /*
        var stub = BankServiceGrpc.newStub(channel);
        stub.getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(2).build(), new StreamObserver<AccountBalance>() {
            @Override
            public void onNext(AccountBalance accountBalance) {
                log.info("Account Balance: {}", accountBalance);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                log.info("Completed");
            }
        });

        log.info("Done");
        Thread.sleep(Duration.ofSeconds(1));
         */

        // Blocking and FutureStub only support Unary and Server streaming
        var stub = BankServiceGrpc.newFutureStub(channel);

    }

}
