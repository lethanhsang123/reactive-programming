package com.sanglt.sec06;

import com.sanglt.models.sec06.TransferRequest;
import com.sanglt.models.sec06.TransferResponse;
import com.sanglt.models.sec06.TransferServiceGrpc;
import com.sanglt.sec06.request_handlers.TransferRequestHandler;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }
}
