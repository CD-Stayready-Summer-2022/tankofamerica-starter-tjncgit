package com.codedifferently.tankofamerica.domain.account.services;

import com.codedifferently.tankofamerica.domain.account.Exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.Exceptions.InsufficientFundsException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repos.AccountRepo;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AccountServiceImpl implements AccountService {
    private static Logger accountLogger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountRepo accountRepo;
    private UserService userService;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, UserService userService) {
        this.accountRepo = accountRepo;
        this.userService = userService;
    }

    public Account create(Long id, Account account) throws UserNotFoundException {
        User owner = userService.getById(id);
        account.setOwner(owner);
        return accountRepo.save(account);
    }

    @Override
    public Account getById(UUID id) throws AccountNotFoundException {
        Optional<Account> optional = Optional.ofNullable(accountRepo.findById(id));
        if(optional.isEmpty())
            throw new AccountNotFoundException("Accout does not exist");
        return optional.get();
    }

    @Override
    public String getAllFromUser(Long userId) throws UserNotFoundException{
        StringBuilder builder = new StringBuilder();
        User owner = userService.getById(userId);
        List<Account> accounts = accountRepo.findByOwner(owner);
        for (Account account: accounts) {
            builder.append(account.toString() + "\n");
        }
        return builder.toString().trim();
    }

    @Override
    public Account update(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Boolean delete(UUID id) throws AccountNotFoundException{
        Account account = getById(id);
        accountRepo.delete(account);
        return true;
    }

    @Override
    public Boolean deposit(UUID id, Long amount) throws AccountNotFoundException {
        Account account = getById(id);
        account.setBalance(account.getBalance() + amount);
        update(account);
        return true;
    }

    @Override
    public Boolean withdraw(UUID id, Long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account account = getById(id);
        if((account.getBalance() < amount))
                throw new InsufficientFundsException("insufficient funds current balance is " + account.getBalance());
        account.setBalance(account.getBalance() - amount);
        update(account);
        return true;
    }

    @Override
    public Boolean transfer(UUID SendFromId, UUID sendToId, Long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account fromAccount = getById(SendFromId);
        Account toAccount = getById(sendToId);
        if ((fromAccount.getBalance() < amount))
            throw new InsufficientFundsException("insufficient funds current balance is " + fromAccount.getBalance());
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        update(fromAccount);
        update(toAccount);
        return true;
    }
}
