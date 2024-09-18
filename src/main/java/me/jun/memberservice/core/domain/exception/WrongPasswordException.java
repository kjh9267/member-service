package me.jun.memberservice.core.domain.exception;

import me.jun.memberservice.support.BusinessException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class WrongPasswordException extends BusinessException {

    private WrongPasswordException(String message) {
        super(message);
        status = UNAUTHORIZED;
    }

    public static WrongPasswordException of(String message) {
        return new WrongPasswordException(message);
    }
}
