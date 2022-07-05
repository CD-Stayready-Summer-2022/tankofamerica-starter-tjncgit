package com.codedifferently.tankofamerica.domain.account.models;

import com.codedifferently.tankofamerica.domain.user.models.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2",strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;
    private String accountName;

    @ManyToOne()
    private User owner;
    Long balance;

    public Account() {
    }

    public Account(String accountName){
        this.accountName = accountName;
        this.balance = 0l;
    }

    public Account(String accountName, User owner) {
        this.accountName = accountName;
        this.owner = owner;
        this.balance = 0l;
    }

    public Account(String accountName, User owner, Long balance) {
        this.accountName = accountName;
        this.owner = owner;
        this.balance = balance;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return String.format("Account for %s named %s with id %s. Current balance: %d", owner.getFirstName(), accountName, id.toString(), balance);
    }
}
