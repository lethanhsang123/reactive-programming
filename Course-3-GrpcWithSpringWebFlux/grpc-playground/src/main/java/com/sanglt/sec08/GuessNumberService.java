package com.sanglt.sec08;

import com.sanglt.models.sec08.GuessNumberGrpc;
import com.sanglt.models.sec08.GuessRequest;
import com.sanglt.models.sec08.GuessResponse;
import com.sanglt.models.sec08.Result;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class GuessNumberService extends GuessNumberGrpc.GuessNumberImplBase {

    private static final Logger log = LoggerFactory.getLogger(GuessNumberService.class);

    @Override
    public StreamObserver<GuessRequest> makeGuess(StreamObserver<GuessResponse> responseObserver) {
        return new GuessRequestHandler(responseObserver);
    }

    private static class GuessRequestHandler implements StreamObserver<GuessRequest> {

        private final StreamObserver<GuessResponse> responseStreamObserver;
        private final int secret;
        private int attempt;

        private GuessRequestHandler(StreamObserver<GuessResponse> responseStreamObserver) {
            this.responseStreamObserver = responseStreamObserver;
            this.attempt = 0;
            this.secret = ThreadLocalRandom.current().nextInt(1, 101);
        }

        @Override
        public void onNext(GuessRequest guessRequest) {
            if (guessRequest.getGuess() > this.secret) {
                this.send(Result.TOO_HIGH);
            } else if (guessRequest.getGuess() < this.secret) {
                this.send(Result.TOO_LOW);
            } else {
                log.info("Client guess {} is correct", guessRequest.getGuess());
                this.send(Result.CORRECT);
                this.responseStreamObserver.onCompleted();
            }
        }

        @Override
        public void onError(Throwable throwable) {
        }

        @Override
        public void onCompleted() {
            this.responseStreamObserver.onCompleted();
        }

        private void send(Result result) {
            this.attempt ++;
            var response = GuessResponse.newBuilder()
                    .setAttempt(this.attempt)
                    .setResult(result)
                    .build();
            this.responseStreamObserver.onNext(response);
        }
    }
}
