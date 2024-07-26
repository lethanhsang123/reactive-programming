package com.sanglt.test.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec11.BankServiceGrpc;
import com.sanglt.models.sec11.Money;
import com.sanglt.models.sec11.WithdrawRequest;
import com.sanglt.sec11.DeadlineBankService;
import com.sanglt.test.common.AbstractChannelTest;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Lec03WaitForReadyTest extends AbstractChannelTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03WaitForReadyTest.class);

    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());
    protected BankServiceGrpc.BankServiceStub bankServiceStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;


    @BeforeAll
    public void setup() {
//        this.grpcServer.start();
        Runnable runnable = () -> {
            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
            this.grpcServer.start();
        };
        // For Java 21
        Thread.ofVirtual().start(runnable);
        // For less than Java21
        /*
        Thread thread = new Thread(runnable);
        thread.start();
         */
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    void blockingDeadlineTest() {
        log.info("Create request");
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();
        Iterator<Money> iterator = this.bankServiceBlockingStub
                .withWaitForReady()
                .withDeadline(Deadline.after(20, TimeUnit.SECONDS))
                .withdraw(request);
        log.info("Call success");
        while (iterator.hasNext()) {
            log.info("Data: {}", iterator.next());
        }
    }


    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

}
