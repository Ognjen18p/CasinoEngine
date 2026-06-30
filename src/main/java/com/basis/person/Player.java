package com.basis.person;

import java.util.Date;
import java.util.Map;

public class Player extends Person {
    private double balance;
    private int successiveWins;
    private Map<Integer, Integer> owningChips;

    public Player() {
    }

    public Player(float balance, int successiveWins, Map<Integer, Integer> owningChips) {
        this.balance = balance;
        this.successiveWins = successiveWins;
        this.owningChips = owningChips;
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

    public Map<Integer, Integer> getOwningChips() {
        return owningChips;
    }

    public void setOwningChips(Map<Integer, Integer> owningChips) {
        this.owningChips = owningChips;
    }

    public void addChip(Integer chipValue) {
        if(owningChips.containsKey(chipValue)) {
            owningChips.put(chipValue, owningChips.get(chipValue) + 1);
            return;
        }
        owningChips.put(chipValue, 1);
    }

    public void removeFromOwningChips(Integer chipValue) {
        if(owningChips.containsKey(chipValue)) {
            if (owningChips.get(chipValue) <= 0) {
                owningChips.remove(chipValue);
                return;
            }
            owningChips.put(chipValue, owningChips.get(chipValue) - 1);
        }
    }

    public Player(int id, String firstName, String lastName, String email, String username, String password, Date dateCreated, float balance, int successiveWins, Map<Integer, Integer> owningChips) {
        super(id, firstName, lastName, email, username, password, dateCreated);
        this.role = Role.PLAYER;
        this.balance = balance;
        this.successiveWins = successiveWins;
        this.owningChips = owningChips;
    }

}
