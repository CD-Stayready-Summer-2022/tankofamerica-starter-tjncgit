package com.codedifferently.tankofamerica.domain.transaction.repos;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.user.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
    Transaction findById(UUID id);

    List<Transaction> findAllByOwner(User owner);
}
