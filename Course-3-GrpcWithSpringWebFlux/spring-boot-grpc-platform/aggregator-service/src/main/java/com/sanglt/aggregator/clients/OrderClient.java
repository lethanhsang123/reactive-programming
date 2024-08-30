package com.sanglt.aggregator.clients;

import com.google.protobuf.Empty;
import com.sanglt.order.OrderServiceGrpc;
import com.sanglt.user.requests.UserInformation;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderClient {

    private static final Logger log = LoggerFactory.getLogger(OrderClient.class);

    @GrpcClient("order-service")
    private OrderServiceGrpc.OrderServiceBlockingStub orderService;

    public com.sanglt.order.response.OrdersInformation getAllOrder() {
        com.sanglt.order.response.OrdersInformation orders = com.sanglt.order.response.OrdersInformation.getDefaultInstance();
        try {
            Empty empty = Empty.newBuilder().build();
            orders = orderService.getAllOrder(empty);
        } catch (final StatusRuntimeException e) {
            log.error("Order-service ERROR: {}", e.getMessage());
        }
        return orders;
    }

    public com.sanglt.order.response.OrdersInformation getOrderByUserId(Integer userId) {
        com.sanglt.order.response.OrdersInformation orders = com.sanglt.order.response.OrdersInformation.getDefaultInstance();
        try {
            UserInformation user = UserInformation.newBuilder()
                    .setUserId(userId)
                    .build();
            orders = orderService.getOrderByUserId(user);
        } catch (final StatusRuntimeException e) {
            log.error("Order-service ERROR: {}", e.getMessage());
        }
        return orders;
    }


}
