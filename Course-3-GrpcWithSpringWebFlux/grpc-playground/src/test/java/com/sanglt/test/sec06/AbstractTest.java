package com.sanglt.test.sec06;


import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec06.BankServiceGrpc;
import com.sanglt.models.sec06.TransferServiceGrpc;
import com.sanglt.sec06.BankService;
import com.sanglt.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer = GrpcServer.create(new BankService());

    protected BankServiceGrpc.BankServiceStub bankServiceStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    protected TransferServiceGrpc.TransferServiceStub transferServiceStub;

    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.transferServiceStub = TransferServiceGrpc.newStub(channel);
    }


    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

}
