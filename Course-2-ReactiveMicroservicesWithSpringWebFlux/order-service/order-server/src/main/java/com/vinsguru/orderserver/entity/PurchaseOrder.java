package com.vinsguru.orderserver.entity;

import com.vinsguru.orderserver.dto.response.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "PurchaseOrder")
@Table(name = "purchase-order")
@ToString
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Integer id;

    private String productId;

    private Integer userId;

    private Integer amount;

    private OrderStatus status;

}
