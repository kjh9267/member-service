package me.jun.memberservice.core.application.exception;

import me.jun.memberservice.support.BusinessException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MemberNotFoundException extends BusinessException {

    private MemberNotFoundException(String email) {
        super(email);
        status = NOT_FOUND;
    }

    public static MemberNotFoundException of(String message) {
        return new MemberNotFoundException(message);
    }
}
