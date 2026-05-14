package com.basis.transaction;

import java.util.Date;

public class Transaction {
    public enum Status {
        PENDING,
        APPROVED,
        DECLINED
    }

    public enum Type {
        DEPOSIT,
        WITHDRAW,
        BET,
        WIN
    }

    private int transactionID;
    private int cardId;
    private int personId;
    private double amount;
    private Status status;
    private Type type;
    private Date timeCreated;
    private Date timeFinished;

    public Transaction(int transactionID, int cardId, int personId, double amount, Status status, Type type, Date timeCreated, Date timeFinished) {
        this.transactionID = transactionID;
        this.cardId = cardId;
        this.personId = personId;
        this.amount = amount;
        this.status = status;
        this.type = type;
        this.timeCreated = timeCreated;
        this.timeFinished = timeFinished;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(Date timeFinished) {
        this.timeFinished = timeFinished;
    }
}
