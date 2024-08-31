package com.sanglt.user.services.clients;

import com.google.protobuf.Timestamp;
import com.sanglt.user.requests.Message;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageRequestHandler implements StreamObserver<Message> {

    private static final Logger log = LoggerFactory.getLogger(MessageRequestHandler.class);

    private final StreamObserver<Message> responseStreamObserver;

    public MessageRequestHandler(StreamObserver<Message> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }

    @Override
    public void onNext(Message message) {
        // Process the incoming message and prepare a response
        log.info("Server handling request: {}", message);

        // Echo back the message to the client
        Message messageResponse = Message.newBuilder()
                .setUserId(message.getUserId())
                .setMessage("Server: " + message.getMessage())
                .setTime(Timestamp.newBuilder().build())
                .build();
        log.info();
        responseStreamObserver.onNext(messageResponse);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}
