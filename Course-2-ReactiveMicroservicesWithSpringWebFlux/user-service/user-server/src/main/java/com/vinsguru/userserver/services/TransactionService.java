package com.vinsguru.userserver.services;

import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import com.vinsguru.userserver.entities.UserTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto dto);
    Flux<UserTransaction> getByUserId(int userId);
}
