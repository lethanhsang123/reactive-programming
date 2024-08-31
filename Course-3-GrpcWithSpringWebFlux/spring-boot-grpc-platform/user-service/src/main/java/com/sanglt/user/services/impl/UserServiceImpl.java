package com.sanglt.user.services.impl;

import com.sanglt.user.UserServiceGrpc;
import com.sanglt.user.requests.Message;
import com.sanglt.user.services.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {

    @Override
    public StreamObserver<Message> chat(StreamObserver<Message> responseObserver) {
        return super.chat(responseObserver);
    }
}
