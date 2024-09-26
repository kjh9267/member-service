package me.jun.memberservice.common.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.support.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ErrorResponse>> businessExceptionHandler(BusinessException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e, e.getStatus(), e.getMessage());
        return Mono.fromSupplier(
                        () -> ResponseEntity.status(e.getStatus())
                                .body(errorResponse)
                )
                .log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            ServerWebInputException.class
    })
    public Mono<ResponseEntity<ErrorResponse>> bindExceptionHandler(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.of(e, BAD_REQUEST, e.getMessage());
        return Mono.fromSupplier(
                        () -> ResponseEntity.badRequest()
                                .body(errorResponse)
                )
                .log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> exceptionHandler(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.of(e, INTERNAL_SERVER_ERROR, e.getMessage());
        return Mono.fromSupplier(
                        () -> ResponseEntity.internalServerError()
                                .body(errorResponse)
                )
                .log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
