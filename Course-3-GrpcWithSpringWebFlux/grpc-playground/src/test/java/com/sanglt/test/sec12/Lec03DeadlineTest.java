package com.sanglt.test.sec12;

import com.sanglt.models.sec12.AccountBalance;
import com.sanglt.models.sec12.BalanceCheckRequest;
import com.sanglt.models.sec12.Money;
import com.sanglt.models.sec12.WithdrawRequest;
import com.sanglt.test.common.ResponseObserver;
import com.sanglt.test.sec12.interceptors.DeadlineInterceptor;
import io.grpc.ClientInterceptor;
import io.grpc.Deadline;
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
    }

    @Test
    public void overrideInterceptorDemo() {
        var request = WithdrawRequest.newBuilder().setAccountNumber(1).setAmount(50).build();
        var response = ResponseObserver.<Money>create();
        this.bankServiceStub
                .withDeadline(Deadline.after(6, TimeUnit.SECONDS))
                .withdraw(request, response);
        response.await();
    }


}
