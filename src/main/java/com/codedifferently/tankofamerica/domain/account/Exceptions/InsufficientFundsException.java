package com.codedifferently.tankofamerica.domain.account.Exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String msg) {
        super(msg);
    }
}
