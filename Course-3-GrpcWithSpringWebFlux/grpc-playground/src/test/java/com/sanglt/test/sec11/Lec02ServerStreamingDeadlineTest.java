package com.sanglt.test.sec11;

import com.sanglt.models.sec11.Money;
import com.sanglt.models.sec11.WithdrawRequest;
import io.grpc.Deadline;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Lec02ServerStreamingDeadlineTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02ServerStreamingDeadlineTest.class);

    @Test
    void blockingDeadlineTest() {
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(40)
                .build();
        Iterator<Money> iterator = this.bankServiceBlockingStub
                .withDeadline(Deadline.after(4, TimeUnit.SECONDS))
                .withdraw(request);
        while (iterator.hasNext()) {
            log.info("Data: {}", iterator.next());
        }
    }

}
