package com.codedifferently.tankofamerica.domain.account.services;

import com.codedifferently.tankofamerica.domain.account.Exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.Exceptions.InsufficientFundsException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repos.AccountRepo;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.services.TransactionService;
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

import static com.codedifferently.tankofamerica.domain.user.controllers.UserController.currentUser;


@Service
public class AccountServiceImpl implements AccountService {
    private static Logger accountLogger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountRepo accountRepo;
    private UserService userService;
    private TransactionService transactionService;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, UserService userService,TransactionService transactionService) {
        this.accountRepo = accountRepo;
        this.userService = userService;
        this.transactionService = transactionService;
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
            throw new AccountNotFoundException("Account does not exist");
        return optional.get();
    }

    @Override
    public Account getByAccountName(String accountName) throws AccountNotFoundException {
        Optional<Account> optional = accountRepo.findByAccountName(accountName);
        if(optional.isEmpty())
            throw new AccountNotFoundException("Account does note exist");
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
    public Boolean deposit(String name, Long amount) throws AccountNotFoundException {
        Account account = getByAccountName(name);
        account.setBalance(account.getBalance() + amount);
        update(account);
        Transaction transaction = new Transaction(amount);
        transaction.setToID(account.getId().toString());
        transaction.setOwner(currentUser);
        transactionService.create(transaction);
        return true;
    }

    @Override
    public Boolean withdraw(String name, Long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account account = getByAccountName(name);
        if((account.getBalance() < amount))
                throw new InsufficientFundsException("insufficient funds current balance is " + account.getBalance());
        account.setBalance(account.getBalance() - amount);
        update(account);
        Transaction transaction = new Transaction(amount);
        transaction.setFromID(account.getId().toString());
        transaction.setOwner(currentUser);
        transactionService.create(transaction);
        return true;
    }

    @Override
    public Boolean transfer(String fromName, String toName, Long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account fromAccount = getByAccountName(fromName);
        Account toAccount = getByAccountName(toName);
        if ((fromAccount.getBalance() < amount))
            throw new InsufficientFundsException("insufficient funds current balance is " + fromAccount.getBalance());
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        update(fromAccount);
        update(toAccount);
        Transaction transaction = new Transaction(fromAccount.getId().toString(), toAccount.getId().toString(), amount);
        transaction.setOwner(currentUser);
        transactionService.create(transaction);
        return true;
    }

    @Override
    public String checkBalance(String name) throws AccountNotFoundException {
        return getByAccountName(name).getBalance().toString();
    }


}
