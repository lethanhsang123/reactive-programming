package com.vinsguru.userserver.util;

import com.vinsguru.userclient.dto.request.TransactionRequestDto;
import com.vinsguru.userclient.dto.request.UserDto;
import com.vinsguru.userclient.dto.response.TransactionResponseDto;
import com.vinsguru.userclient.dto.response.TransactionStatus;
import com.vinsguru.userserver.entiry.User;
import com.vinsguru.userserver.entiry.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static UserTransaction toEntity(TransactionRequestDto request) {
        UserTransaction entity = new UserTransaction();
        entity.setUserId(request.getUserId());
        entity.setAmount(request.getAmount());
        entity.setTransactionDate(LocalDateTime.now());
        return entity;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto request, TransactionStatus status) {
        TransactionResponseDto response = new TransactionResponseDto();
        response.setAmount(response.getAmount());
        response.setUserId(request.getUserId());
        response.setStatus(status);
        return response;
    }

}
