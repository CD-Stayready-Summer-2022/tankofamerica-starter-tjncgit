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

@ShellComponent
public class AccountController {
   private AccountService accountService;

   @Autowired
   public AccountController(AccountService accountService) {
       this.accountService = accountService;
   }

   @ShellMethod("Create a new Account")
   public String createNewAccount(@ShellOption({"-U", "--user"}) Long id,
                                   @ShellOption({"-N", "--name"}) String accountName){
       try {
           Account account = new Account(accountName);
           account = accountService.create(id, account);
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

   @ShellMethod("Get all accounts")
   public String getAllAccounts(@ShellOption({"-U", "--user"}) Long id) {
       try {
           String accounts = accountService.getAllFromUser(id);
           return accounts;
       } catch (UserNotFoundException e) {
           return "the user id is invalid";
       }
   }

   @ShellMethod("Get all accounts")
   public String getAccount(@ShellOption({"-I", "--id"}) UUID id) {
       try {
           Account account = accountService.getById(id);
           return account.toString();
       } catch (AccountNotFoundException e) {
           return "account does not exist";
       }
   }

   @ShellMethod("Add money to account")
   public Boolean deposit(@ShellOption({"-I", "--id"}) UUID id, @ShellOption({"-A", "--amount"}) Long amount) {
       try {
           return accountService.deposit(id, amount);
       } catch (AccountNotFoundException e) {
           System.out.println(e.getMessage());
           return false;
       }
   }

   @ShellMethod("Withdraw money from account")
   public Boolean withdraw(@ShellOption({"-I", "--id"}) UUID id, @ShellOption({"-A", "--amount"}) Long amount) {
       try {
           return accountService.withdraw(id, amount);
       } catch (AccountNotFoundException e) {
           System.out.println(e.getMessage());
           return false;
       } catch (InsufficientFundsException e) {
           System.out.println(e.getMessage());
           return false;
       }
   }

   @ShellMethod("transfer money to another account")
   public Boolean transfer(@ShellOption({"-F", "--from"}) UUID fromId, @ShellOption({"-F", "--from"}) UUID toId, @ShellOption({"-A", "--amount"}) Long amount) {
       try {
           return accountService.transfer(fromId,toId, amount);
       } catch (AccountNotFoundException e) {
           System.out.println(e.getMessage());
           return false;
       } catch (InsufficientFundsException e) {
           System.out.println(e.getMessage());
           return false;
       }
   }
}
