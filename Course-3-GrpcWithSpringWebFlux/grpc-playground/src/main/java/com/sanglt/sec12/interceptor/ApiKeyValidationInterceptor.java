package com.sanglt.sec12.interceptor;

import com.sanglt.sec12.Constants;
import io.grpc.*;

import java.util.Objects;

public class ApiKeyValidationInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        var apiKey = metadata.get(Constants.API_KEY);
        if (isValid(apiKey)) {
            return serverCallHandler.startCall(serverCall, metadata);
        }
        serverCall.close(Status.UNAUTHENTICATED.withDescription("Client must provide valid data"),
                metadata
        );
        return new ServerCall.Listener<ReqT>() {
        };
    }

    private boolean isValid(String apiKey) {
        return Objects.nonNull(apiKey) && "bank-client-secret".equals(apiKey);
    }
}
