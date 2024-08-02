package com.sanglt.test.sec06;

import com.sanglt.models.sec06.TransferRequest;
import com.sanglt.models.sec06.TransferResponse;
import com.sanglt.models.sec06.TransferStatus;
import com.sanglt.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lec05BiDirectionalStreamingTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec05BiDirectionalStreamingTest.class);

    @Test
    public void transferTest() {
        var responseObserver = ResponseObserver.<TransferResponse>create();
        var requestObserver = this.transferServiceStub.transfer(responseObserver);
        var requests = List.of(
                TransferRequest.newBuilder().setAmount(10).setFromAccount(6).setToAccount(6).build(),
                TransferRequest.newBuilder().setAmount(110).setFromAccount(6).setToAccount(7).build(),
                TransferRequest.newBuilder().setAmount(10).setFromAccount(6).setToAccount(7).build(),
                TransferRequest.newBuilder().setAmount(10).setFromAccount(7).setToAccount(6).build()
        );
        requests.forEach(requestObserver::onNext);
        requestObserver.onCompleted();
        responseObserver.await();

        Assertions.assertEquals(4, responseObserver.getItems().size());
        this.validate(responseObserver.getItems().get(0), TransferStatus.REJECTED, 100, 100);
        this.validate(responseObserver.getItems().get(1), TransferStatus.REJECTED, 100, 100);
        this.validate(responseObserver.getItems().get(2), TransferStatus.COMPLETED, 100, 100);
        this.validate(responseObserver.getItems().get(3), TransferStatus.REJECTED, 100, 100);
    }

    private void validate(TransferResponse response, TransferStatus status, int fromAccountBalance, int toAccountBalance) {
        Assertions.assertEquals(status, response.getStatus());
        Assertions.assertEquals(fromAccountBalance, response.getFromAccount().getBalance());
        Assertions.assertEquals(toAccountBalance, response.getToAccount().getBalance());
    }

}
