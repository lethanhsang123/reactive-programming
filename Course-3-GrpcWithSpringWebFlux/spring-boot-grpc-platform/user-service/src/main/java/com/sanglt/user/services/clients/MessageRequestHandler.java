package com.sanglt.user.services.clients;

import com.google.protobuf.Timestamp;
import com.sanglt.user.request.MessageRequest;
import com.sanglt.user.response.MessageResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageRequestHandler implements StreamObserver<MessageRequest> {

    private static final Logger log = LoggerFactory.getLogger(MessageRequestHandler.class);

    private final StreamObserver<MessageResponse> responseStreamObserver;

    public MessageRequestHandler(StreamObserver<MessageResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }

    @Override
    public void onNext(MessageRequest request) {
        // Process the incoming message and prepare a response
        log.info("Server handling request: {}", request);

        // Echo back the message to the client
        MessageResponse response = MessageResponse.newBuilder()
                .setUserId(request.getUserId())
                .setMessage("Server: " + request.getMessage())
                .setTime(Timestamp.newBuilder().build())
                .build();
        log.info("Server sending response: {}", response);
        responseStreamObserver.onNext(response);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Handling error: {}", throwable.getMessage());
        responseStreamObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }

    @Override
    public void onCompleted() {
        log.info("Server completed...");
        responseStreamObserver.onCompleted();
    }
}
