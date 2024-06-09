package com.vinsguru.userclient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionRequestDto {

    @JsonProperty(value = "user_id")
    private Integer userId;
    private Integer amount;

}
