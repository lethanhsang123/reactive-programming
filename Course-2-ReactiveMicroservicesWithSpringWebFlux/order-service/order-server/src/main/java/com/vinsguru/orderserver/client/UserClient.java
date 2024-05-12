package com.vinsguru.orderserver.client;

import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import reactor.core.publisher.Mono;

public interface UserClient {
    Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto request);
}
