package com.codedifferently.tankofamerica.domain.transaction.services;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.repos.TransactionRepo;
import com.codedifferently.tankofamerica.domain.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{
    private TransactionRepo transactionRepo;

    @Autowired
    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return transactionRepo.save(transaction);

    }

    @Override
    public Transaction getById(UUID id) {
        return transactionRepo.findById(id);
    }

    @Override
    public String getAllTransactions(User owner) {
        StringBuilder builder = new StringBuilder();
        List<Transaction> transactions = transactionRepo.findAllByOwner(owner);
        for (Transaction transaction: transactions) {
            builder.append(transaction.toString() + "\n");
        }
        return builder.toString().trim();
    }

}
