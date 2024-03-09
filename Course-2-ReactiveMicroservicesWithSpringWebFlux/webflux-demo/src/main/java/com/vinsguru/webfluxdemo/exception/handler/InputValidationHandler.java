package com.vinsguru.webfluxdemo.exception.handler;

import com.vinsguru.webfluxdemo.dto.InputFailedValidationResponse;
import com.vinsguru.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {


    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException exception) {
        InputFailedValidationResponse response = new InputFailedValidationResponse();
        response.setErrorCode(exception.getErrorCode());
        response.setMessage(exception.getMessage());
        response.setInput(exception.getInput());
        return ResponseEntity.badRequest().body(response);
    }

}
