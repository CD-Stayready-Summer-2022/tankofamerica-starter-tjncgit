package com.codedifferently.tankofamerica.domain.user.models;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Set;
@Service
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Account> accounts;
    @OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Transaction> transactions;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return String.format("%d %s %s %s %s", id, firstName, lastName, email, password);
    }
}
