package com.sanglt.order.services.impl;

import com.google.protobuf.Empty;
import com.sanglt.common.ErrorCode;
import com.sanglt.order.OrderServiceGrpc;
import com.sanglt.order.response.OrderInformation;
import com.sanglt.order.response.OrdersInformation;
import com.sanglt.order.services.OrderService;
import com.sanglt.order.utils.Constants;
import com.sanglt.order.utils.GRPCMetadataUtils;
import com.sanglt.user.request.UserInformation;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.*;


@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase implements OrderService {


    private static final Map<Integer, List<OrderInformation>> orderMap = Map.of(
            1,
            new ArrayList<>(List.of(
                    OrderInformation.newBuilder().setOrderId(1).setDescription("ORDER 1").build(),
                    OrderInformation.newBuilder().setOrderId(3).setDescription("ORDER 3").build(),
                    OrderInformation.newBuilder().setOrderId(4).setDescription("ORDER 4").build(),
                    OrderInformation.newBuilder().setOrderId(6).setDescription("ORDER 6").build()
            )),
            2,
            new ArrayList<>(List.of(
                    OrderInformation.newBuilder().setOrderId(2).setDescription("ORDER 2").build(),
                    OrderInformation.newBuilder().setOrderId(5).setDescription("ORDER 5").build(),
                    OrderInformation.newBuilder().setOrderId(7).setDescription("ORDER 7").build(),
                    OrderInformation.newBuilder().setOrderId(8).setDescription("ORDER 8").build()
            )),
            3,
            new ArrayList<>(List.of(
                    OrderInformation.newBuilder().setOrderId(9).setDescription("ORDER 9").build(),
                    OrderInformation.newBuilder().setOrderId(10).setDescription("ORDER 10").build(),
                    OrderInformation.newBuilder().setOrderId(11).setDescription("ORDER 11").build(),
                    OrderInformation.newBuilder().setOrderId(12).setDescription("ORDER 12").build()
            ))
    );

    @Override
    public void getOrderByUserId(UserInformation request, StreamObserver<OrdersInformation> responseObserver) {
        userInformationRequestValidate(request)
                .ifPresentOrElse(
                        responseObserver::onError,
                        () -> responseObserver.onNext(
                                OrdersInformation.newBuilder()
                                        .addAllOrders(orderMap.get(request.getUserId()).parallelStream().toList())
                                        .build()
                        )
                );
        responseObserver.onCompleted();
    }

    @Override
    public void getAllOrder(Empty request, StreamObserver<OrdersInformation> responseObserver) {
        orderDataValidate().
                ifPresentOrElse(
                        responseObserver::onError,
                        () -> responseObserver.onNext(OrdersInformation.newBuilder().addAllOrders(orderMap.values().stream().flatMap(List::stream).toList()).build())
                );
        responseObserver.onCompleted();
    }

    private Optional<StatusRuntimeException> orderDataValidate() {
        StatusRuntimeException statusRuntimeException = null;
        if (orderMap.isEmpty()) {
            statusRuntimeException = GRPCMetadataUtils.toStatusRuntimeException(
                    ErrorCode.RESOURCE_NOT_FOUND_ERROR,
                    GRPCMetadataUtils.toMetadata(Constants.ERROR_DESCRIPTION, "Not found order information", null));
        }
        return Optional.ofNullable(statusRuntimeException);
    }

    private Optional<StatusRuntimeException> userInformationRequestValidate(UserInformation request) {
        StatusRuntimeException statusRuntimeException = null;
        if (Objects.isNull(request)) {
            Metadata metadata = GRPCMetadataUtils.toMetadata(
                    Constants.ERROR_DESCRIPTION,
                    "User information cannot empty",
                    null);
            return Optional.of(GRPCMetadataUtils.toStatusRuntimeException(ErrorCode.VALIDATION_ERROR, metadata));
        }
        if (!orderMap.containsKey(request.getUserId())) {
            Metadata metadata = GRPCMetadataUtils.toMetadata(
                    Constants.ERROR_DESCRIPTION,
                    "Not found order information", null);
            statusRuntimeException = GRPCMetadataUtils.toStatusRuntimeException(ErrorCode.RESOURCE_NOT_FOUND_ERROR, metadata);
        }
        return Optional.ofNullable(statusRuntimeException);
    }

}
