package me.jun.memberservice.common.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.support.BusinessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Mono<ErrorResponse> businessExceptionHandler(BusinessException e) {
        return Mono.fromSupplier(
                () -> ErrorResponse.builder(e, e.getStatus(), e.getMessage())
                        .build()
        )
                .log()
                .doOnError(throwable -> log.error("{}", throwable));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            NoResourceFoundException.class,
            ServerWebInputException.class
    })
    public Mono<ErrorResponse> bindExceptionHandler(Exception e) {
        return Mono.fromSupplier(
                () -> ErrorResponse.builder(e, BAD_REQUEST, e.getMessage())
                        .build()
        )
                .log()
                .doOnError(throwable -> log.error("{}", throwable));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> exceptionHandler(Exception e) {
        return Mono.fromSupplier(
                () -> ErrorResponse.builder(e, INTERNAL_SERVER_ERROR, e.getMessage())
                        .build()
        )
                .log()
                .doOnError(throwable -> log.error("{}", throwable));
    }
}
