package com.vinsguru.orderserver.clients;

import com.vinsguru.orderserver.dtos.requests.clients.TransactionRequestDto;
import com.vinsguru.orderserver.dtos.responses.clients.TransactionResponseDto;
import reactor.core.publisher.Mono;

public interface UserClient {
    Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto request);
}
