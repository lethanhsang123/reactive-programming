package com.vinsguru.userserver.entities;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table(name = "users")
public class Users {
    @Id
    private Integer id;
    private String name;
    private Integer balance;

}
