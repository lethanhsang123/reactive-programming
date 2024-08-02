package com.sanglt.test.sec10;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec10.BankServiceGrpc;
import com.sanglt.models.sec10.ErrorMessage;
import com.sanglt.models.sec10.ValidationCode;
import com.sanglt.sec10.BankService;
import com.sanglt.test.common.AbstractChannelTest;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.Optional;

public abstract class AbstractTest extends AbstractChannelTest {

    private static final Metadata.Key<ErrorMessage> ERROR_MESSAGE_KEY = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());

    private final GrpcServer grpcServer = GrpcServer.create(new BankService(), new com.sanglt.sec09.BankService());

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

    protected ValidationCode getValidationCode(Throwable throwable) {
        return Optional.ofNullable(Status.trailersFromThrowable(throwable))
                .map(m -> m.get(ERROR_MESSAGE_KEY))
                .map(ErrorMessage::getValidationCode)
                .orElseThrow();
    }

}
