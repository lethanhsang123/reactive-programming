package com.sanglt.test.sec11;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec11.BankServiceGrpc;
import com.sanglt.sec11.DeadlineBankService;
import com.sanglt.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());

    protected BankServiceGrpc.BankServiceStub bankServiceStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;


    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }


    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

}
