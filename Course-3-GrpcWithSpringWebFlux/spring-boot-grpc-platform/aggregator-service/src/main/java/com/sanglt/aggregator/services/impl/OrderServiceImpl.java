package com.sanglt.aggregator.services.impl;

import com.sanglt.aggregator.clients.OrderClient;
import com.sanglt.aggregator.models.responses.OrderResponse;
import com.sanglt.aggregator.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderClient orderClient;

    public OrderServiceImpl(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<OrderResponse> orders = new ArrayList<>();
        com.sanglt.order.response.OrdersInformation ordersInformation = orderClient.getAllOrder();
        ordersInformation.getOrdersOrBuilderList().forEach(order ->
                orders.add(new OrderResponse(order.getOrderId(), order.getDescription())));
        return orders;
    }
    @Override
    public List<OrderResponse> getOrderByUserId(Integer userId) {
        List<OrderResponse> orders = new ArrayList<>();
        com.sanglt.order.response.OrdersInformation ordersInformation = orderClient.getOrderByUserId(userId);
        ordersInformation.getOrdersOrBuilderList().forEach(order ->
                orders.add(new OrderResponse(order.getOrderId(), order.getDescription())));
        return orders;
    }
}
