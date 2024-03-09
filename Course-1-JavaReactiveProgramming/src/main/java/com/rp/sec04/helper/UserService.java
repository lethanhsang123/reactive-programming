package com.rp.sec04.helper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserService {

    public static Flux<User> getUsers() {
        return Flux.range(1, 2)
                .map(User::new);
    }

    public static Mono<User> getUser() {
        return Mono.just(new User(1));
    }

}
