package com.sanglt.aggregator.controllers;

import com.sanglt.aggregator.models.responses.OrderResponse;
import com.sanglt.aggregator.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder() {
        List<OrderResponse> orderResponse = orderService.getAllOrder();
        return ResponseEntity.ok(orderResponse);
    }

}
