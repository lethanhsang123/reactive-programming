package com.sanglt.test.sec11;

import com.sanglt.models.sec11.AccountBalance;
import com.sanglt.models.sec11.BalanceCheckRequest;
import com.sanglt.test.common.ResponseObserver;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Lec01UnaryDeadlineTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01UnaryDeadlineTest.class);

    @Test
    public void blockingDeadlineTest() {

        var ex = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(1)
                    .build();

            var response = this.bankServiceBlockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .getAccountBalance(request);
        });
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, ex.getStatus().getCode());



//        log.info("Response: {}", response);

    }

    @Test
    public void asyncDeadlineTest() {
        var observer = ResponseObserver.<AccountBalance>create();
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();

        this.bankServiceStub
                .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                .getAccountBalance(request, observer);
        observer.await();
        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, Status.fromThrowable(observer.getThrowable()).getCode());
    }


}
