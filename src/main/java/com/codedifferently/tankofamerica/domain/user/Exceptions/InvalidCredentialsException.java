package com.codedifferently.tankofamerica.domain.user.Exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
