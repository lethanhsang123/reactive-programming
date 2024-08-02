package com.sanglt.test.sec12;

import com.sanglt.common.GrpcServer;
import com.sanglt.models.sec12.BalanceCheckRequest;
import com.sanglt.sec12.BankService;
import com.sanglt.sec12.Constants;
import com.sanglt.sec12.interceptor.ApiKeyValidationInterceptor;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lec05ClientApiKeyInterceptor extends AbstractInterceptorTest {

    private static final Logger log = LoggerFactory.getLogger(Lec05ClientApiKeyInterceptor.class);
    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(
                MetadataUtils.newAttachHeadersInterceptor(getApiKey())
        );
    }

    @Override
    public GrpcServer create() {
        return GrpcServer.create(6565, builder -> {
            builder.addService(new BankService());
            builder.intercept(new ApiKeyValidationInterceptor());
        });
    }

    @Test
    public void clientApiKeyDemo() {
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
        var response = this.bankServiceBlockingStub.getAccountBalance(request);
        log.info("Response: {}", response);
    }

    private Metadata getApiKey() {
        var metadata = new Metadata();
        metadata.put(Constants.API_KEY, "bank-client-secret");
        return metadata;
    }


}
