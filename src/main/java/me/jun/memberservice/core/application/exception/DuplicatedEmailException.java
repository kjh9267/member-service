package me.jun.memberservice.core.application.exception;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException(String email) {
        super(email);
    }
}
