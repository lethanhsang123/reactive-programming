package com.sanglt.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import com.sanglt.models.sec11.*;
import com.sanglt.sec06.repository.AccountRepository;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DeadlineBankService extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(DeadlineBankService.class);

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        /*
        Ideally we should do some input validation.
        But we are going to assume only happy path scenarios.
        Because, in gRPC, there are multiple ways to communicate the error message to client.
        It has to be discussed in detail separately
        Assumption: account # 1 - 10 & with draw amount is multiple of $10
         */

        var accountNumber = request.getAccountNumber();
        var requestedAmount = request.getAmount();
        var accountBlance = AccountRepository.getBalance(accountNumber);

        if (requestedAmount > accountBlance) {
            // This is error case. But will change it to proper error later
            responseObserver.onError(Status.FAILED_PRECONDITION.asRuntimeException());
            return;
        }

        for (int i = 0; i < (requestedAmount / 10) && !Context.current().isCancelled() ; i++) {
            var money = Money.newBuilder().setAmount(10).build();
            responseObserver.onNext(money);
            log.info("Money sent {}", money);
            AccountRepository.deductAmount(accountNumber, 10);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
        log.info("Streaming completed");
        responseObserver.onCompleted();

    }
}
