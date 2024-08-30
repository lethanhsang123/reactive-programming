package com.sanglt.order.exceptions.grpc;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}