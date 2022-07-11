package com.codedifferently.tankofamerica.domain.account.controllers;

import com.codedifferently.tankofamerica.domain.account.Exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.Exceptions.InsufficientFundsException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.UUID;

import static com.codedifferently.tankofamerica.domain.user.controllers.UserController.currentUser;

@ShellComponent
public class AccountController {
   private AccountService accountService;

   @Autowired
   public AccountController(AccountService accountService) {
       this.accountService = accountService;
   }

   @ShellMethod("Create a new Account")
   public String createNewAccount(@ShellOption({"-N", "--name"}) String accountName){
       try {
           Account account = new Account(accountName);
           account = accountService.create(currentUser.getId(), account);
           return account.toString();
       } catch (UserNotFoundException e) {
           return "The User Id is invalid";
       }
   }

   @ShellMethod("Remove Existing Account")
   public boolean removeAccount(@ShellOption({"-U", "--user"}) UUID id){
       try {
           return accountService.delete(id);
       } catch (AccountNotFoundException e) {
           System.out.println(e.getMessage());
           return false;
       }
   }

   @ShellMethod("Get all accounts from Current User")
   public String getAllAccounts() {
       try {
            return accountService.getAllFromUser(currentUser.getId());
       } catch (UserNotFoundException e) {
           return e.getMessage();
       }
   }

   @ShellMethod("Get all accounts")
   public String getAccount(@ShellOption({"-N", "--name"}) String name) {
       try {
           Account account = accountService.getByAccountName(name);
           return account.toString();
       } catch (AccountNotFoundException e) {
           return "account does not exist";
       }
   }

   @ShellMethod("Add money to account")
   public String deposit(@ShellOption({"-N", "--name"}) String name, @ShellOption({"-A", "--amount"}) Long amount) {
       try {
           accountService.deposit(name, amount);
           return String.format("deposit of %d into %s account complete", amount, name);
       } catch (AccountNotFoundException e) {
           return e.getMessage();
       }
   }

   @ShellMethod("Withdraw money from account")
   public String withdraw(@ShellOption({"-N", "--name"}) String name, @ShellOption({"-A", "--amount"}) Long amount) {
       try {
           accountService.withdraw(name, amount);
           return String.format("withdraw of %d from %s account complete", amount, name);
       } catch (AccountNotFoundException e) {
           return e.getMessage();
       } catch (InsufficientFundsException e) {
            return e.getMessage();
       }
   }

   @ShellMethod("transfer money to another account")
   public String transfer(@ShellOption({"-F", "--from"}) String fromName, @ShellOption({"-T", "--to"}) String toName, @ShellOption({"-A", "--amount"}) Long amount) {
       try {
           accountService.transfer(fromName,toName, amount);
           return String.format("transfer of %d from %s account to %s account complete", amount, fromName, toName);
       } catch (AccountNotFoundException e) {
           return e.getMessage();
       } catch (InsufficientFundsException e) {
           return e.getMessage();
       }
   }

   @ShellMethod("check account balance")
    public String checkAccountBalance(@ShellOption({"-N", "--name"}) String accountName) {
       try {
           return String.format("%s balance: $%s", accountName, accountService.checkBalance(accountName));
       } catch (AccountNotFoundException e) {
           return e.getMessage();
       }
   }
}
