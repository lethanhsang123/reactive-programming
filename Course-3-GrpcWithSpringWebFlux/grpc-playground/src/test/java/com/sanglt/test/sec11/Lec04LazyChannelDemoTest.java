package com.sanglt.test.sec11;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec11.BalanceCheckRequest;
import com.sanglt.models.sec11.BankServiceGrpc;
import com.sanglt.sec11.DeadlineBankService;
import com.sanglt.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04LazyChannelDemoTest extends AbstractChannelTest {

    private final Logger log = LoggerFactory.getLogger(Lec04LazyChannelDemoTest.class);
    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());

    protected BankServiceGrpc.BankServiceStub bankServiceStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;


    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void lazyChannelDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankServiceBlockingStub.getAccountBalance(request);
        log.info("Data = {}", response);
    }


    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

}
