package com.sanglt.order.services;

import com.google.protobuf.Empty;
import com.sanglt.order.OrderServiceGrpc;
import com.sanglt.order.response.OrdersInformation;
import com.sanglt.order.services.impl.OrderService;
import com.sanglt.user.requests.UserInformation;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase implements OrderService {

    @Override
    public void getOrderByUserId(UserInformation request, StreamObserver<OrdersInformation> responseObserver) {
        com.sanglt.order.response.OrderInformation order1 = com.sanglt.order.response.OrderInformation.newBuilder()
                .setOrderId(1)
                .setDescription("ORDER 1")
                .build();
        com.sanglt.order.response.OrderInformation order2 = com.sanglt.order.response.OrderInformation.newBuilder()
                .setOrderId(2)
                .setDescription("ORDER 2")
                .build();
        com.sanglt.order.response.OrdersInformation orderInformation = OrdersInformation.newBuilder()
                .addOrders(order1)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .addOrders(order2)
                .build();
        responseObserver.onNext(orderInformation);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllOrder(Empty request, StreamObserver<OrdersInformation> responseObserver) {
        com.sanglt.order.response.OrderInformation order1 = com.sanglt.order.response.OrderInformation.newBuilder()
                .setOrderId(1)
                .setDescription("ORDER 1")
                .build();
        com.sanglt.order.response.OrderInformation order2 = com.sanglt.order.response.OrderInformation.newBuilder()
                .setOrderId(2)
                .setDescription("ORDER 2")
                .build();
        com.sanglt.order.response.OrdersInformation orderInformation = OrdersInformation.newBuilder()
                .addOrders(order1)
                .addOrders(order2)
                .build();
        responseObserver.onNext(orderInformation);
        responseObserver.onCompleted();
    }
}
