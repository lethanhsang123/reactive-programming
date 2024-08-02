package com.sanglt.sec10;

import com.google.common.util.concurrent.Uninterruptibles;
import com.sanglt.models.sec10.*;
import com.sanglt.sec10.repository.AccountRepository;
import com.sanglt.sec10.validator.RequestValidator;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        RequestValidator.validateAccount(request.getAccountNumber())
                .ifPresentOrElse(
                        responseObserver::onError,
                        () -> sendAccountBalance(request, responseObserver)
                );

        responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }

    private void sendAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();
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
        RequestValidator.validateAccount(request.getAccountNumber())
                .or(() -> RequestValidator.isAmountDivisibleBy10(request.getAmount()))
                .or(() -> RequestValidator.hasSufficientBalance(request.getAmount(), AccountRepository.getBalance(request.getAccountNumber())))
                .ifPresentOrElse(
                        responseObserver::onError,
                        () -> this.sendMoney(request, responseObserver)
                );
    }

    private void sendMoney(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        try {
            var accountNumber = request.getAccountNumber();
            var requestedAmount = request.getAmount();
            for (int i = 0; i < (requestedAmount / 10); i++) {
                if (i == 3) {
                    throw new RuntimeException();
                }
                var money = Money.newBuilder().setAmount(10).build();
                responseObserver.onNext(money);
                log.info("Money sent {}", money);
                AccountRepository.deductAmount(accountNumber, 10);
                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException()
            );
        }
    }
}
