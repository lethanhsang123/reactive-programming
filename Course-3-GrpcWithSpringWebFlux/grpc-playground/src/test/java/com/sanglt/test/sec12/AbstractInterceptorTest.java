package com.sanglt.test.sec12;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec12.BankServiceGrpc;
import com.sanglt.sec12.BankService;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractInterceptorTest {
    private final GrpcServer grpcServer = GrpcServer.create(new BankService());
    private ManagedChannel channel;
    protected BankServiceGrpc.BankServiceStub bankServiceStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    protected abstract List<ClientInterceptor> getClientInterceptors();

    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .intercept(getClientInterceptors())
                .build();
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }


    @AfterAll
    public void stop() {
        this.grpcServer.stop();
        this.channel.shutdownNow();
    }
}
