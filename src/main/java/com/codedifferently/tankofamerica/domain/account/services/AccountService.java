package com.codedifferently.tankofamerica.domain.account.services;

import com.codedifferently.tankofamerica.domain.account.Exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.Exceptions.InsufficientFundsException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account create(Long id, Account account) throws UserNotFoundException;
    Account getById(UUID id) throws AccountNotFoundException;
    String getAllFromUser(Long userId) throws UserNotFoundException;
    Account update(Account account);
    Boolean delete(UUID id) throws AccountNotFoundException;
    Boolean deposit(UUID id ,Long amount) throws AccountNotFoundException;
    Boolean withdraw(UUID id, Long amount) throws AccountNotFoundException, InsufficientFundsException;

    Boolean transfer(UUID SendFromId, UUID sendToId, Long amount) throws AccountNotFoundException, InsufficientFundsException;

}
