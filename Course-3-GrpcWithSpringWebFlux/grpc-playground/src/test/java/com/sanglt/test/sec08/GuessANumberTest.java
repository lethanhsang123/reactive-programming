package com.sanglt.test.sec08;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec08.GuessNumberGrpc;
import com.sanglt.sec08.GuessNumberService;
import com.sanglt.test.common.AbstractChannelTest;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuessANumberTest extends AbstractChannelTest {

    private static final Logger log = LoggerFactory.getLogger(GuessANumberTest.class);

    private final GrpcServer server = GrpcServer.create(new GuessNumberService());
    private GuessNumberGrpc.GuessNumberStub stub;

    @BeforeAll
    public void setup() {
        this.server.start();
        this.stub = GuessNumberGrpc.newStub(channel);
    }

    @Test
    public void guessANumberGame() {
        var responseObserver = new GuessResponseHandler();
        var requestObserver = this.stub.makeGuess(responseObserver);
        responseObserver.setRequestStreamObserver(requestObserver);
        responseObserver.start();
        responseObserver.await();
        log.info("=========================");
    }

    @AfterAll
    public void stop() {
        this.server.stop();
    }

}
