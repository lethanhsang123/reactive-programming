package com.vinsguru.userclient.service;

import com.vinsguru.userclient.client.UserTransactionClient;
import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class UserTransactionService {

    public static UserTransactionClient client(WebClient webClient) {
        return new UserTransactionClientImpl(webClient);
    }

    private static class UserTransactionClientImpl implements UserTransactionClient {

        private final WebClient webClient;

        public UserTransactionClientImpl(WebClient webClient) {
            this.webClient = webClient;
        }

        @Override
        public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto request) {
            return this.webClient
                    .post()
                    .uri("/user/transactions")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(TransactionResponseDto.class);
        }
    }

}
