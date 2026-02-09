package com.basis.game.basis;

public abstract class Game {
    protected int bet;
    protected int lastBet;
    protected int totalWin;
    protected int lastWin;

    public Game(int bet, int lastBet, int totalWin, int lastWin) {
        this.bet = bet;
        this.lastBet = lastBet;
        this.totalWin = totalWin;
        this.lastWin = lastWin;
    }

    public Game() {
    }

    public int getBet() {
        return bet;
    }

    @Override
    public String toString() {
        return "Game{" +
                "bet=" + bet +
                ", lastBet=" + lastBet +
                ", totalWin=" + totalWin +
                ", lastWin=" + lastWin +
                '}';
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getLastBet() {
        return lastBet;
    }

    public void setLastBet(int lastBet) {
        this.lastBet = lastBet;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(int totalWin) {
        this.totalWin = totalWin;
    }

    public int getLastWin() {
        return lastWin;
    }

    public void setLastWin(int lastWin) {
        this.lastWin = lastWin;
    }
}
