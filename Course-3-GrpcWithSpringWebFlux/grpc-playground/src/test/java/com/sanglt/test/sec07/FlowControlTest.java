package com.sanglt.test.sec07;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec07.FlowControlServiceGrpc;
import com.sanglt.sec07.FlowControlService;
import com.sanglt.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class FlowControlTest extends AbstractChannelTest {

    private final GrpcServer server = GrpcServer.create(new FlowControlService());
    private FlowControlServiceGrpc.FlowControlServiceStub stub;


    @BeforeAll
    public void setup() {
        this.server.start();
        this.stub = FlowControlServiceGrpc.newStub(channel);
    }

    @AfterAll
    public void stop() {
        this.server.stop();
    }

}
