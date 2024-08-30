package com.sanglt.order.services.advices.grpc;

import com.sanglt.common.ErrorCode;
import com.sanglt.order.exceptions.grpc.ResourceNotFoundException;
import com.sanglt.order.exceptions.grpc.ValidationException;
import com.sanglt.order.utils.Constants;
import com.sanglt.order.utils.GRPCMetadataUtils;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcAdvice
public class GlobalGrpcExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalGrpcExceptionHandler.class);

    @GrpcExceptionHandler(ResourceNotFoundException.class)
    public StatusRuntimeException handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("gRPC ResourceNotFoundException: {}", e.getMessage());
        Metadata metadata = GRPCMetadataUtils.toMetadata(Constants.ERROR_DESCRIPTION, e.getMessage(), null);
        return GRPCMetadataUtils.toStatusRuntimeException(ErrorCode.RESOURCE_NOT_FOUND_ERROR, metadata);
    }

    @GrpcExceptionHandler(ValidationException.class)
    public StatusRuntimeException handleValidationException(ValidationException e) {
        log.error("gRPC ValidationException: {}", e.getMessage());
        Metadata metadata = GRPCMetadataUtils.toMetadata(Constants.ERROR_DESCRIPTION, e.getMessage(), null);
        return GRPCMetadataUtils.toStatusRuntimeException(ErrorCode.VALIDATION_ERROR, metadata);
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleGenericException(Exception e) {
        log.error("gRPC System error: {}", e.getMessage(), e);
        Metadata metadata = GRPCMetadataUtils.toMetadata(Constants.ERROR_DESCRIPTION, e.getMessage(), null);
        return GRPCMetadataUtils.toStatusRuntimeException(ErrorCode.SYSTEM_ERROR, metadata);
    }

}
