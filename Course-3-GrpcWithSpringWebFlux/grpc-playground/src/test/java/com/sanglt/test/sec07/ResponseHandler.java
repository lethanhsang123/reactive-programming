package com.sanglt.test.sec07;

import com.google.common.util.concurrent.Uninterruptibles;
import com.sanglt.models.sec07.Output;
import com.sanglt.models.sec07.RequestSize;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ResponseHandler implements StreamObserver<Output> {

    private static final Logger log = LoggerFactory.getLogger(ResponseHandler.class);
    private final CountDownLatch latch = new CountDownLatch(1);
    private StreamObserver<RequestSize> requestStreamObserver;
    private int size;

    @Override
    public void onNext(Output output) {
        this.size--;
        this.process(output);
        if (this.size == 0) {
            log.info("------------------");
            this.request(3);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        this.requestStreamObserver.onCompleted();
        log.info("Completed");
    }

    public void setRequestStreamObserver(StreamObserver<RequestSize> requestStreamObserver) {
        this.requestStreamObserver = requestStreamObserver;
    }

    private void request(int size) {
        log.info("Requesting size {}", size);
        this.size = size;
        this.requestStreamObserver.onNext(RequestSize.newBuilder().setSize(size).build());
    }

    private void process(Output output) {
        log.info("Received {}", output);
        Uninterruptibles.sleepUninterruptibly(
                ThreadLocalRandom.current().nextInt(50, 200),
                TimeUnit.MICROSECONDS
        );
    }

    public void await() {
        try {
            this.latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        this.request(3);
    }

}
