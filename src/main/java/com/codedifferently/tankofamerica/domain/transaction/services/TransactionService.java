package com.codedifferently.tankofamerica.domain.transaction.services;

import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.user.models.User;

import java.util.UUID;

public interface TransactionService {
    Transaction create(Transaction transaction);
    Transaction getById(UUID id);
    String getAllTransactions(User owner);

}
