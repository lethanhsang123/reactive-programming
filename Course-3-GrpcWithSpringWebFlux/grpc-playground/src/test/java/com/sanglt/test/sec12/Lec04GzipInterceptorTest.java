package com.sanglt.test.sec12;

import com.sanglt.models.sec12.BalanceCheckRequest;
import com.sanglt.test.sec12.interceptors.DeadlineInterceptor;
import com.sanglt.test.sec12.interceptors.GzipRequestInterceptor;
import io.grpc.ClientInterceptor;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Lec04GzipInterceptorTest extends AbstractInterceptorTest {

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(new GzipRequestInterceptor(), new DeadlineInterceptor(Duration.of(2, ChronoUnit.SECONDS)));
    }

    @Test
    public void gzipDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        this.bankServiceBlockingStub.getAccountBalance(request);
    }
}
