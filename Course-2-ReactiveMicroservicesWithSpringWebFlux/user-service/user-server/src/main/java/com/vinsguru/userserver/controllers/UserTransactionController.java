package com.vinsguru.userserver.controllers;

import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import com.vinsguru.userserver.entities.UserTransaction;
import com.vinsguru.userserver.services.TransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/user/transactions")
public class UserTransactionController {

    private final TransactionService transactionService;

    public UserTransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> request) {
        return request.flatMap(this.transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("user_id") int userId) {
        return this.transactionService.getByUserId(userId);
    }
}
