package com.sanglt.sec12.interceptor;

import com.google.common.base.Strings;
import com.sanglt.sec12.Constants;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

/*  SRS
    1. user-token-1, user-token-2   => PRIME users, all calls are allowed
    2. user-token-3, user-token-4   => STANDARD users, server streaming calls are NOT allowed. other calls are allowed.
    3. Any other token              => Not valid
 */

public class UserTokenInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserTokenInterceptor.class);
    private static final Set<String> PRIME_TOKEN_SET = Set.of("user-token-1", "user-token-2");
    private static final Set<String> STANDARD_TOKEN_SET = Set.of("user-token-2", "user-token-3");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        //  Get token from Metadata
        var token = extractToken(metadata.get(Constants.USER_TOKEN_KEY));

        //  Check token valid
        if (!isValid(token)) {
            return close(serverCall, metadata, Status.UNAUTHENTICATED.withDescription("Token is either null or invalid"));
        }

        // Check not streaming data
        boolean isOneMessage = serverCall.getMethodDescriptor().getType().serverSendsOneMessage();

        // Check token permission
        if (!isOneMessage && !PRIME_TOKEN_SET.contains(token)) {
            return close(serverCall, metadata, Status.PERMISSION_DENIED.withDescription("User is not allowed to do this operation"));
        }
        
        return serverCallHandler.startCall(serverCall, metadata);
    }

    private <ReqT, RespT> ServerCall.Listener<ReqT> close(ServerCall<ReqT, RespT> serverCall, Metadata metadata, Status status) {
        serverCall.close(status, metadata);
        return new ServerCall.Listener<ReqT>() {};
    }

    private boolean isValid(String token) {
        return !Strings.isNullOrEmpty(token) && (PRIME_TOKEN_SET.contains(token) || STANDARD_TOKEN_SET.contains(token));
    }

    private String extractToken(String value) {
        return Objects.nonNull(value) && value.startsWith(Constants.BEARER) ?
                value.substring(Constants.BEARER.length()).trim()
                : null;
    }
}
