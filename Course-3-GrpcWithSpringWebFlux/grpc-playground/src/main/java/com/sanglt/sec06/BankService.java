package com.sanglt.sec06;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import com.sanglt.models.sec06.*;
import com.sanglt.sec06.repository.AccountRepository;
import com.sanglt.sec06.request_handlers.DepositRequestHandler;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();
        responseObserver.onNext(accountBalance);
        log.info("Get account balance onNext");
        responseObserver.onCompleted();
        log.info("Get account balance onCompleted");
    }

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountResponse> responseObserver) {
        var accounts = AccountRepository.getAllAccounts()
                .entrySet()
                .stream()
                .map(e -> AccountBalance.newBuilder().setAccountNumber(e.getKey()).setBalance(e.getValue()).build())
                .toList();
        var response = AllAccountResponse.newBuilder().addAllAccounts(accounts).build();
        responseObserver.onNext(response);
        log.info("Get all account onNext");
        responseObserver.onCompleted();
        log.info("Get all account onCompleted");
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
            responseObserver.onCompleted();
            return;
        }

        for (int i = 0; i < (requestedAmount / 10); i++) {
            var money = Money.newBuilder().setAmount(10).build();
            responseObserver.onNext(money);
            log.info("Money sent {}", money);
            AccountRepository.deductAmount(accountNumber, 10);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<DepositRequest> deposit(StreamObserver<AccountBalance> responseObserver) {
        return new DepositRequestHandler(responseObserver);
    }
}
