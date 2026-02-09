package com.basis.person;

import java.util.Date;

public class Player extends Person{
    private float balance;
    private int successiveWins;
//    private Card savedCard;


    public Player(float balance, int successiveWins) {
        this.balance = balance;
        this.successiveWins = successiveWins;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getSuccessiveWins() {
        return successiveWins;
    }

    public void setSuccessiveWins(int successiveWins) {
        this.successiveWins = successiveWins;
    }

    public Player(int id, String name, String surname, String email, String username, String password, Role role, Date dateCreated, float balance, int successiveWins) {
        super(id, name, surname, email, username, password, role, dateCreated);
        this.balance = 0;
        this.successiveWins = 0;
    }

    @Override
    public Role getRole() {
        return Role.PLAYER;
    }
}
