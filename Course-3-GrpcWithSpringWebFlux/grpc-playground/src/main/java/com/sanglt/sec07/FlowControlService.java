package com.sanglt.sec07;

import com.sanglt.models.sec07.FlowControlServiceGrpc;
import com.sanglt.models.sec07.Output;
import com.sanglt.models.sec07.RequestSize;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class FlowControlService extends FlowControlServiceGrpc.FlowControlServiceImplBase {

    public static final Logger log = LoggerFactory.getLogger(FlowControlService.class);

    @Override
    public StreamObserver<RequestSize> getMessages(StreamObserver<Output> responseObserver) {
        return new FlowControlService.RequestHandler(responseObserver);
    }

    private static class RequestHandler implements StreamObserver<RequestSize> {

        private final StreamObserver<Output> responseObserver;
        private Integer emitted;    // To keep track the number of messages emitted so far

        private RequestHandler(StreamObserver<Output> responseObserver) {
            this.responseObserver = responseObserver;
            this.emitted = 0;
        }

        @Override
        public void onNext(RequestSize requestSize) {
            IntStream.rangeClosed((emitted + 1), 100)
                    .limit(requestSize.getSize())
                    .forEach(i -> {
                        log.info("Emitting {}", i);
                        this.responseObserver.onNext(Output.newBuilder().setValue(i).build());
                    });
            this.emitted = this.emitted + requestSize.getSize();
            if (emitted >= 100) {
                this.responseObserver.onCompleted();
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onCompleted() {
            this.responseObserver.onCompleted();
        }
    }
}
