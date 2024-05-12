package com.vinsguru.orderserver.clients.impl;

import com.vinsguru.orderserver.clients.UserClient;
import com.vinsguru.orderserver.dtos.requests.clients.TransactionRequestDto;
import com.vinsguru.orderserver.dtos.responses.clients.TransactionResponseDto;
import com.vinsguru.orderserver.utils.CommonParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
        return this.webClient
                .post()
                .uri(CommonParam.ServiceURI.UserService.CREATE_TRANSACTION)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }
}
