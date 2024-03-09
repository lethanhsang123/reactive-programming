package com.vinsguru.orderserver.client.impl;

import com.vinsguru.orderserver.client.UserTransactionClient;
import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import com.vinsguru.userclient.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserTransactionClientImpl implements UserTransactionClient {
    private final WebClient webClient;

    public UserTransactionClientImpl(@Value("${user.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto request) {
        return UserTransactionService.client(this.webClient).createTransaction(request);
    }
}
