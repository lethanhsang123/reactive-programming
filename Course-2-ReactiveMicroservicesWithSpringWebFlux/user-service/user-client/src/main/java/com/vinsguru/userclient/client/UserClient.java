package com.vinsguru.userclient.client;

import com.vinsguru.userclient.dto.request.UserDto;
import reactor.core.publisher.Mono;

public interface UserClient {

    Mono<UserDto> getById(Integer id);

}
