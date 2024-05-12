package com.vinsguru.orderserver.client.impl;

import com.vinsguru.orderserver.client.UserClient;
import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.request.UserDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import com.vinsguru.userclient.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClientImpl implements UserClient {
    private final WebClient webClient;

    public UserClientImpl(@Value("${user.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto request) {
        return UserTransactionService.client(this.webClient).createTransaction(request);
    }

    public Flux<UserDto> getAllUsers() {
        return this.webClient
                .get()
                .uri("all")
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}
