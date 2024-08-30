package com.sanglt.order.utils;

import com.google.protobuf.Message;
import com.sanglt.common.ErrorCode;
import com.sanglt.common.ErrorMessage;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;

import java.util.Objects;

public class GRPCMetadataUtils {

    public static StatusRuntimeException toStatusRuntimeException(ErrorCode errorCode, Metadata metadata) {
        if (Objects.isNull(metadata)) {
            metadata = new Metadata();
        }
        metadata = toMetadata(errorCode, metadata);
        return errorCodeToStatus(errorCode).asRuntimeException(metadata);
    }

    public static Metadata toMetadata(ErrorCode errorCode, Metadata metadata) {
        String errorMessage =
                switch (errorCode) {
                    case SYSTEM_ERROR -> Constants.SYSTEM_ERROR_MESSAGE;
                    case RESOURCE_NOT_FOUND_ERROR -> Constants.RESOURCE_NOT_FOUND_ERROR_MESSAGE;
                    case VALIDATION_ERROR -> Constants.VALIDATION_ERROR_MESSAGE;
                    default -> Constants.ERROR_MESSAGE;
                };
        ErrorMessage errorMessageData = ErrorMessage.newBuilder()
                .setErrorCode(errorCode)
                .setErrorMessage(errorMessage)
                .build();

        return toMetadata(Constants.ERROR_MESSAGE_KEY, errorMessageData, metadata);
    }

    public static <T> Metadata toMetadata(Metadata.Key<T> key, T value, Metadata metadata) {
        if (Objects.isNull(metadata)) { metadata = new Metadata(); }
        if (Objects.nonNull(key) && Objects.nonNull(value)) { metadata.put(key, value);}
        return metadata;
    }

    public static <T extends Message> Metadata.Key<T> toMetadataKey(T value) {
        return ProtoUtils.keyForProto(value);
    }

    public static Status errorCodeToStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case RESOURCE_NOT_FOUND_ERROR -> Status.NOT_FOUND.withDescription(Constants.RESOURCE_NOT_FOUND_ERROR_MESSAGE);
            case VALIDATION_ERROR -> Status.INVALID_ARGUMENT.withDescription(Constants.VALIDATION_ERROR_MESSAGE);
            default -> Status.UNKNOWN.withDescription(Constants.ERROR_MESSAGE);
        };
    }

}
