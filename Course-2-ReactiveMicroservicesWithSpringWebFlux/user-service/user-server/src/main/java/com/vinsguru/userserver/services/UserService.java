package com.vinsguru.userserver.services;

import com.vinsguru.userclient.dto.request.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserDto> all();

    Mono<UserDto> getByUserId(final int userId);

    Mono<UserDto> createUser(Mono<UserDto> dto);

    Mono<UserDto> updateUser(int id, Mono<UserDto> dto);

    Mono<Void> deleteUser(int id);
}
