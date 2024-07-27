package com.sanglt.test.sec11;

import com.sanglt.test.common.AbstractChannelTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    It is class demo the eager channel creation behavior
    There is a bug: https://github.com/grpc/grpc-java/issues/10517
 */
public class Lec05EagerChannelDemoTest extends AbstractChannelTest {
    private static final Logger log = LoggerFactory.getLogger(Lec05EagerChannelDemoTest.class);

    @Test
    public void eagerChannelDemo() {
        log.info("Test: {}", channel.getState(Boolean.TRUE));
    }
}
