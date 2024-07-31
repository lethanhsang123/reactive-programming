package com.sanglt.test.sec12;

import com.sanglt.models.sec12.AccountBalance;
import com.sanglt.models.sec12.BalanceCheckRequest;
import com.sanglt.test.common.ResponseObserver;
import com.sanglt.test.sec12.interceptors.DeadlineInterceptor;
import io.grpc.ClientInterceptor;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec03DeadlineTest extends AbstractInterceptorTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03DeadlineTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(new DeadlineInterceptor(Duration.ofSeconds(2)));
    }


    @Test
    public void blockingDeadlineTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankServiceBlockingStub.getAccountBalance(request);
        log.info("Response: {}", response);
    }

    @Test
    public void asyncDeadlineTest() {
        var observer = ResponseObserver.<AccountBalance>create();
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();

        this.bankServiceStub.getAccountBalance(request, observer);
        observer.await();
        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, Status.fromThrowable(observer.getThrowable()).getCode());
    }

    @Test
    public void overrideInterceptorDemo() {
        
    }


}
