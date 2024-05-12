package com.vinsguru.userserver.services.impl;

import com.vinsguru.userclient.dto.request.UserDto;
import com.vinsguru.userserver.repositories.UserRepository;
import com.vinsguru.userserver.services.UserService;
import com.vinsguru.userserver.utils.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Flux<UserDto> all() {
        return this.userRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<UserDto> getByUserId(final int userId) {
        return this.userRepository.findById(userId)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<UserDto> createUser(Mono<UserDto> dto) {
        return dto
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<UserDto> updateUser(int id, Mono<UserDto> dto) {
        return this.userRepository.findById(id)
                .flatMap(u -> dto.map(EntityDtoUtil::toEntity).doOnNext(e -> e.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<Void> deleteUser(int id) {
        return this.userRepository.deleteById(id);
    }
}
