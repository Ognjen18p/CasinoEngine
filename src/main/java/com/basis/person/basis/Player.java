package com.basis.person.basis;

import java.util.Date;

public class Player extends Person{
    private float balance;
    private int successiveWins;
//    private Card savedCard;


    public Player(float balance, int successiveWins) {
        this.balance = balance;
        this.successiveWins = successiveWins;
    }

    public Player(int id, String name, String surname, String email, Role role, Date dateCreated, float balance, int successiveWins) {
        super(id, name, surname, email, role, dateCreated);
        this.balance = balance;
        this.successiveWins = successiveWins;
    }

    @Override
    public Role getRole() {
        return Role.PLAYER;
    }
}
