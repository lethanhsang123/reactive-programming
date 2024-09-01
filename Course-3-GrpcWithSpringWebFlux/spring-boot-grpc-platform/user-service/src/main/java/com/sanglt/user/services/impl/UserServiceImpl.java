package com.sanglt.user.services.impl;

import com.sanglt.user.UserServiceGrpc;
import com.sanglt.user.request.MessageRequest;
import com.sanglt.user.response.MessageResponse;
import com.sanglt.user.services.UserService;
import com.sanglt.user.services.clients.MessageRequestHandler;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {
    @Override
    public StreamObserver<MessageRequest> chat(StreamObserver<MessageResponse> responseObserver) {
        return new MessageRequestHandler(responseObserver);
    }

}
