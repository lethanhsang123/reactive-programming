package com.sanglt.aggregator.clients;

import com.sanglt.aggregator.clients.handlers.ChatResponseHandler;
import com.sanglt.user.UserServiceGrpc;
import com.sanglt.user.request.MessageRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserClient {

    private static final Logger log = LoggerFactory.getLogger(UserClient.class);

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceStub userServiceStub;


    public void chat() {
        StreamObserver<MessageRequest> rq = this.userServiceStub.chat(new ChatResponseHandler());
        List<Integer> numbers = List.of(1, 2 ,3 ,4);
        numbers.forEach(integer -> {
            MessageRequest request = MessageRequest.newBuilder()
                    .setUserId(integer)
                    .setMessage("Message from user " + integer)
                    .build();
            log.info("Client sending request: {}", request);
            rq.onNext(request);
        });
        rq.onCompleted();
    }

}
