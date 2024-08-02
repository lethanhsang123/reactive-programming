package com.sanglt.test.sec06;

import com.google.protobuf.Empty;
import com.sanglt.models.sec06.AccountBalance;
import com.sanglt.models.sec06.AllAccountResponse;
import com.sanglt.models.sec06.BalanceCheckRequest;
import com.sanglt.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec02UnaryAsyncClientTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02UnaryAsyncClientTest.class);

    @Test
    public void getBalanceTest() {
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.bankServiceStub.getAccountBalance(request, observer);
        observer.await();
        Assertions.assertEquals(1, observer.getItems().size());
        Assertions.assertEquals(100, observer.getItems().getFirst().getBalance());
        Assertions.assertNull(observer.getThrowable());

    }

    @Test
    public void getAllAccount() {
        var observer = ResponseObserver.<AllAccountResponse>create();
        this.bankServiceStub.getAllAccounts(Empty.getDefaultInstance(), observer);
        observer.await();
        Assertions.assertEquals(1, observer.getItems().size());
        Assertions.assertEquals(100, observer.getItems().getFirst().getAccountsCount());
        Assertions.assertNull(observer.getThrowable());
    }


}
