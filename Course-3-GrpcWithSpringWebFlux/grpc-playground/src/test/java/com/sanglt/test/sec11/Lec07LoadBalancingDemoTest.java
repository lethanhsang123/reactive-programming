package com.sanglt.test.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import com.sanglt.models.sec06.*;
import com.sanglt.test.common.ResponseObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec07LoadBalancingDemoTest {

    private static final Logger log = LoggerFactory.getLogger(Lec07LoadBalancingDemoTest.class);
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;
    private ManagedChannel channel;

    @BeforeAll
    public void setup() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext()
                .build();
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void loadBalancingDemo() {
        for (int i = 1; i < 10 ; i++) {
            var request = BalanceCheckRequest.newBuilder().setAccountNumber(i).build();
            var response = this.bankServiceBlockingStub.getAccountBalance(request);
            log.info("{}", response);
        }
    }
    @Test
    public void loadBalancingDemo2() {
        var responseObserver = ResponseObserver.<AccountBalance>create();
        var requestObserver = this.bankServiceStub.deposit(responseObserver);

        // Init message - account number
        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());

        // Sending stream of money
        IntStream.rangeClosed(1, 30)
                .mapToObj(i -> Money.newBuilder().setAmount(10).build())
                .map(m -> DepositRequest.newBuilder().setMoney(m).build())
                .forEach( d -> {
                    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
                    requestObserver.onNext(d);
                });

        requestObserver.onCompleted();

        responseObserver.await();
    }

    @AfterAll
    public void stop() {
        this.channel.shutdownNow();
    }

}
