package com.vinsguru.userserver.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinsguru.userserver.utils.CommonParams;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@ToString
@Table(name = "user_transactions")
public class UserTransaction {

    @Id
    private Integer id;

    @Column(value = "user_id")
    private Integer userId;

    private Integer amount;

    @Column(value = "transaction_date")
    @JsonFormat(pattern= CommonParams.StringPool.DATE_TIME_FORMAT_DD_MM_YYYY_HH_MM)
    private LocalDateTime transactionDate;

}
