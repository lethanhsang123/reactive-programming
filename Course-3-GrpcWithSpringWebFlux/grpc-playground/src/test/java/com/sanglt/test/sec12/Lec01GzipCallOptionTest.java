package com.sanglt.test.sec12;

import com.sanglt.models.sec12.BalanceCheckRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01GzipCallOptionTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec01GzipCallOptionTest.class);

    @Test
    public void gzipDemo() {
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
//        CompressorRegistry.getDefaultInstance().register();
        var response = this.bankServiceBlockingStub
                .withCompression("gzip")
                .getAccountBalance(request);
        log.info("{}", response);
    }
}
