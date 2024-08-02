package com.sanglt.test.sec12;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec12.BalanceCheckRequest;
import com.sanglt.models.sec12.Money;
import com.sanglt.models.sec12.WithdrawRequest;
import com.sanglt.sec12.BankService;
import com.sanglt.sec12.Constants;
import com.sanglt.sec12.interceptor.ApiKeyValidationInterceptor;
import com.sanglt.sec12.interceptor.UserTokenInterceptor;
import com.sanglt.test.common.ResponseObserver;
import io.grpc.CallCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class Lec06UserSessionTokenInterceptorTest extends AbstractInterceptorTest {

    private static final Logger log = LoggerFactory.getLogger(Lec06UserSessionTokenInterceptorTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return Collections.emptyList();
    }

    @Override
    public GrpcServer create() {
        return GrpcServer.create(6565, builder -> {
            builder.addService(new BankService());
            builder.intercept(new UserTokenInterceptor());
        });
    }

    @Test
    public void unaryUserCredentialDemo() {
        for (int i = 1; i < 5; i++) {
            var request = BalanceCheckRequest.newBuilder().setAccountNumber(i).build();
            var response = this.bankServiceBlockingStub
                    .withCallCredentials(new UserSessionToken("user-token-" + i))
                    .getAccountBalance(request);
            log.info("Response: {}", response);
        }
    }
    @Test
    public void streamingUserCredentialDemo() {
        for (int i = 1; i < 5; i++) {
            var request = WithdrawRequest.newBuilder().setAccountNumber(i).setAmount(30).build();
            var responseObserver = ResponseObserver.<Money>create();
            this.bankServiceStub.withCallCredentials(new UserSessionToken("user-token-" + i)).withdraw(request, responseObserver);
            responseObserver.await();
        }
    }

    private static class UserSessionToken extends CallCredentials {

        private final String TOKEN_FORMAT = "%s %s";

        private final String jwt;

        private UserSessionToken(String jwt) {
            this.jwt = jwt;
        }

        @Override
        public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
            /*  Sync
            var metadata = new Metadata();
            metadata.put(Constants.USER_TOKEN_KEY, TOKEN_FORMAT.formatted(Constants.BEARER, jwt));
            metadataApplier.apply(metadata);
             */
            // Async
            executor.execute(() -> {
                var metadata = new Metadata();
                metadata.put(Constants.USER_TOKEN_KEY, TOKEN_FORMAT.formatted(Constants.BEARER, jwt));
                metadataApplier.apply(metadata);
            });
        }
    }

}
