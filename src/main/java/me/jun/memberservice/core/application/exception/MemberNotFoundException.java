package me.jun.memberservice.core.application.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String email) {
        super(email);
    }
}
