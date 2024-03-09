package com.vinsguru.webfluxdemo.config;

import com.vinsguru.webfluxdemo.dto.InputFailedValidationResponse;
import com.vinsguru.webfluxdemo.exception.InputValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    private final RequestHandler requestHandler;

    public RouterConfig(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("/router", this::serverResponseRouterFunction)
                .path("/router1", this::serverResponseRouterFunction1)
                .build();
    }

//    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction1() {
        return RouterFunctions.route()
                .GET("/square/{input}", this.requestHandler::squareHandler)
                .GET("/table/{input}", this.requestHandler::tableHandler)
                .GET("/table/{input}/stream", this.requestHandler::tableStreamHandler)
                .POST("/multiply", this.requestHandler::multiplyHandler)
                .GET("/square/{input}/validation", this.requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

//    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {

        RequestPredicate predicate1 = RequestPredicates.path("/router/square/1?");
        RequestPredicate predicate2 = RequestPredicates.path("/router/square/20");

        return RouterFunctions.route()
                .GET("/square/{input}", predicate1.or(predicate2),this.requestHandler::squareHandler)
                .GET("/square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10 - 19 allowed"))
                .GET("/table/{input}", this.requestHandler::tableHandler)
                .GET("/table/{input}/stream", this.requestHandler::tableStreamHandler)
                .POST("/multiply", this.requestHandler::multiplyHandler)
                .GET("/square/{input}/validation", this.requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }


    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (throwable, serverRequest) -> {
            InputValidationException ex = (InputValidationException) throwable;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(ex.getErrorCode());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }

}
