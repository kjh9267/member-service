package me.jun.memberservice.core.application.exception;

import me.jun.memberservice.support.BusinessException;

import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicatedEmailException extends BusinessException {

    private DuplicatedEmailException(String email) {
        super(email);
        this.status = CONFLICT;
    }

    public static DuplicatedEmailException of(String message) {
        return new DuplicatedEmailException(message);
    }
}
