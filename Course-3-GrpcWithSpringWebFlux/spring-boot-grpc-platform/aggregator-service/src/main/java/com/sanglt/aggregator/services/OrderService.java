package com.sanglt.aggregator.services;

import com.sanglt.aggregator.models.responses.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAllOrder();
    List<OrderResponse> getOrderByUserId(Integer userId);
}
