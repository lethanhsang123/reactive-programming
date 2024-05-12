package com.vinsguru.userserver.repositories;

import com.vinsguru.userserver.entities.Users;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<Users, Integer> {

    @Modifying
    @Query(
            " update users " +
            " set balance = balance - (:amount) " +
            " where id = (:userId) and balance >= (:amount) "
    )
    Mono<Boolean> updateUserBalance(int userId, int amount);

}
