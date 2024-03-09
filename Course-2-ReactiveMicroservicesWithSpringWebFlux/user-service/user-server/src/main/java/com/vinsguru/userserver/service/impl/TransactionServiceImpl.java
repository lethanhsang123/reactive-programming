package com.vinsguru.userserver.service.impl;

import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import com.vinsguru.userclient.dto.response.TransactionStatus;
import com.vinsguru.userserver.entiry.UserTransaction;
import com.vinsguru.userserver.repository.UserRepository;
import com.vinsguru.userserver.repository.UserTransactionRepository;
import com.vinsguru.userserver.service.TransactionService;
import com.vinsguru.userserver.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;

    private final UserTransactionRepository userTransactionRepository;

    public TransactionServiceImpl(UserRepository userRepository, UserTransactionRepository userTransactionRepository) {
        this.userRepository = userRepository;
        this.userTransactionRepository = userTransactionRepository;
    }

    @Override
    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto dto) {
        return this.userRepository.updateUserBalance(dto.getUserId(), dto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(dto))
                .flatMap(this.userTransactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(dto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(dto, TransactionStatus.DECLINED));
    }

    @Override
    public Flux<UserTransaction> getByUserId(int userId) {
        return this.userTransactionRepository.findByUserId(userId);
    }
}
