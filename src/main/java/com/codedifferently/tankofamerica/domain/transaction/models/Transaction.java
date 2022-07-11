package com.codedifferently.tankofamerica.domain.transaction.models;

import com.codedifferently.tankofamerica.domain.user.models.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2",strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;
    private String fromID;
    private String toID;
    private Long transferAmount;
    @ManyToOne
    private User owner;

    public Transaction() {
    }

    public Transaction(Long transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Transaction(String fromID, String toID, Long transferAmount) {
        this.fromID = fromID;
        this.toID = toID;
        this.transferAmount = transferAmount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public Long getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Long transferAmount) {
        this.transferAmount = transferAmount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String toString(){
        return String.format("transactionID: %s from: %s to: %s amount: %d initiator: %s", id.toString(), fromID, toID, transferAmount, owner.getId());
    }
}
