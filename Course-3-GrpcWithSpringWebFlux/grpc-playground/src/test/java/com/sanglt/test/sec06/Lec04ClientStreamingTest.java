package com.sanglt.test.sec06;

import com.sanglt.models.sec06.AccountBalance;
import com.sanglt.models.sec06.DepositRequest;
import com.sanglt.models.sec06.Money;
import com.sanglt.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class Lec04ClientStreamingTest extends AbstractTest {

    @Test
    public void depositTest() {
        var responseObserver = ResponseObserver.<AccountBalance>create();
        var requestObserver = this.bankServiceStub.deposit(responseObserver);

        // Initial message - account number
        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());
        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Money.newBuilder().setAmount(10).build())
                .map(m -> DepositRequest.newBuilder().setMoney(m).build())
                .forEach(requestObserver::onNext);

        // Notifying the server that we are done
        requestObserver.onCompleted();

        // At this point out response observer should receive a response
        responseObserver.await();

        Assertions.assertEquals(1, responseObserver.getItems().size());
        Assertions.assertEquals(200, responseObserver.getItems().getFirst().getBalance());
        Assertions.assertNull(responseObserver.getThrowable());
    }

}
