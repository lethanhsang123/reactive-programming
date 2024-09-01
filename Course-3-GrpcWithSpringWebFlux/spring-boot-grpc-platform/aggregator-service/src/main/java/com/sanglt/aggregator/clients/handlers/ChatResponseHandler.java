package com.sanglt.aggregator.clients.handlers;

import com.sanglt.user.response.MessageResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatResponseHandler implements StreamObserver<MessageResponse> {

    private static final Logger log = LoggerFactory.getLogger(ChatResponseHandler.class);

    @Override
    public void onNext(MessageResponse messageResponse) {
        log.info("Client handling message response: {}", messageResponse);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Client error handling: {}", throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        log.info("Client completed...");
    }
}
