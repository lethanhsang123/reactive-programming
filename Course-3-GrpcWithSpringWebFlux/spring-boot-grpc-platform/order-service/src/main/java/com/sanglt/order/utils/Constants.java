package com.sanglt.order.utils;

import com.sanglt.common.ErrorMessage;
import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.protobuf.ProtoUtils;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public interface Constants {

    Metadata.Key<ErrorMessage> ERROR_MESSAGE_KEY = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());
    Metadata.Key<String> ERROR_DESCRIPTION = Metadata.Key.of("desc", ASCII_STRING_MARSHALLER);
    Context.Key<String> USER_ID_CTX_KEY = Context.key("userId");
    Context.Key<String> JWT_CTX_KEY = Context.key("jwt");
    Context.Key<String> TRACE_ID_CTX_KEY = Context.key("traceId");
    Context.Key<String> CLIENT_ID_KEY = Context.key("clientId");
    Metadata.Key<String> CLIENT_ID_MD_KEY = Metadata.Key.of("client-id", ASCII_STRING_MARSHALLER);
    Metadata.Key<String> JWT_METADATA_KEY = Metadata.Key.of("jwt", ASCII_STRING_MARSHALLER);
    Metadata.Key<String> TRACE_ID_METADATA_KEY = Metadata.Key.of("traceId", ASCII_STRING_MARSHALLER);

    String ERROR_MESSAGE = "ERROR_MESSAGE";
    String SYSTEM_ERROR_MESSAGE = "SYSTEM_ERROR_MESSAGE";
    String RESOURCE_NOT_FOUND_ERROR_MESSAGE = "RESOURCE_NOT_FOUND_ERROR_MESSAGE";
    String VALIDATION_ERROR_MESSAGE = "VALIDATION_ERROR_MESSAGE";

}
