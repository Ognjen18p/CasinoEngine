package com.basis.person;

import java.util.Date;

public class Player extends Person{
    private double balance;
    private int successiveWins;
//    private Card savedCard;


    public Player() {
    }

    public Player(float balance, int successiveWins) {
        this.balance = balance;
        this.successiveWins = successiveWins;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getSuccessiveWins() {
        return successiveWins;
    }

    public void setSuccessiveWins(int successiveWins) {
        this.successiveWins = successiveWins;
    }

    public Player(int id, String firstName, String lastName, String email, String username, String password, Date dateCreated, float balance, int successiveWins) {
        super(id, firstName, lastName, email, username, password, dateCreated);
        this.role = Role.PLAYER;
        this.balance = 0;
        this.successiveWins = 0;
    }

}
