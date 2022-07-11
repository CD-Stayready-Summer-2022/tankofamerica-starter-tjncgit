package com.codedifferently.tankofamerica.domain.transaction.controllers;

import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static com.codedifferently.tankofamerica.domain.user.controllers.UserController.currentUser;

@ShellComponent
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ShellMethod("show all transactions for current user")
    public String showTransactions() {
        return transactionService.getAllTransactions(currentUser);
    }
}
