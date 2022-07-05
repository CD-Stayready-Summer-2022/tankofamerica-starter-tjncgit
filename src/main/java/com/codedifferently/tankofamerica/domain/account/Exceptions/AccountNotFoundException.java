package com.codedifferently.tankofamerica.domain.account.Exceptions;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
