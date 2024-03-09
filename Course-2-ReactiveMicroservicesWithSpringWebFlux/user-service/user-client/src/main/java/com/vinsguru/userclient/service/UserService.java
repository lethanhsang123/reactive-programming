package com.vinsguru.userclient.service;

import com.vinsguru.userclient.client.UserClient;
import com.vinsguru.userclient.dto.request.UserDto;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class UserService {

    public static UserClient client(WebClient webClient) {
        return new UserClientImpl(webClient);
    }


    private static class UserClientImpl implements UserClient{
        private final WebClient webClient;

        public UserClientImpl(WebClient webClient) {
            this.webClient = webClient;
        }


        @Override
        public Mono<UserDto> getById(Integer id) {
            Objects.requireNonNull(id);
            return this.webClient
                    .get()
                    .uri("/users/{id}", id)
                    .retrieve()
                    .bodyToMono(UserDto.class);
        }

    }


}
