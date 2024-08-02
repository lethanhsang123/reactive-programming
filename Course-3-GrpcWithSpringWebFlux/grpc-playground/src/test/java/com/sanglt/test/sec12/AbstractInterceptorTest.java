package com.sanglt.test.sec12;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec12.BankServiceGrpc;
import com.sanglt.sec12.BankService;
import com.sanglt.sec12.interceptor.GzipResponseInterceptor;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractInterceptorTest {
    private GrpcServer grpcServer;
    private ManagedChannel channel;
    protected BankServiceGrpc.BankServiceStub bankServiceStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    protected abstract List<ClientInterceptor> getClientInterceptors();


    public GrpcServer create() {
        return GrpcServer.create(6565, builder ->
           builder.addService(new BankService())
                   .intercept(new GzipResponseInterceptor())
        );
    }

    @BeforeAll
    public void setup() {
        this.grpcServer = this.create();
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
