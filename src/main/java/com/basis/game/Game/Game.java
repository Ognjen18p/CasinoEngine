package com.basis.game.Game;

import com.basis.game.BlackJack.Chip;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class Game {
    protected Pane mainPane;
    protected Button depositButton;
    protected Button exitButton;
    protected Label winLabel;
    protected Label lastWinLabel;
    protected Label totalWinLabel;
    protected Label textMessageLabel;
    protected Label betLabel;
    protected Label balanceLabel;
    protected ObservableList<Chip> owningChips;
    protected ArrayList<Chip> bettingChips;

    protected int balance;
    protected int bet;
    protected int lastBet;
    protected int win;
    protected int totalWin;
    protected int lastWin;

    protected boolean isPlaying = false;

    protected void initializeElements() {
        bet = 0;
        balance = 1000;
        lastBet = lastBet;
        totalWin = totalWin;
        lastWin = lastWin;
        win = 0;

        mainPane = new Pane();
        depositButton = new Button("Deposit");
        exitButton = new Button("Exit");
        totalWinLabel = new Label(Integer.toString(totalWin));
        lastWinLabel = new Label(Integer.toString(lastWin));
        betLabel = new Label(Integer.toString(bet));
    }

    public int getBet() {
        return bet;
    }

    @Override
    public String toString() {
        return "Game{" +
                "balance=" + balance +
                ", bet=" + bet +
                ", lastBet=" + lastBet +
                ", totalWin=" + totalWin +
                ", lastWin=" + lastWin +
                '}';
    }

    public void setBet(int bet) {
        betLabel.setText(Integer.toString(bet));
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

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
        winLabel.setText(Integer.toString(win));
    }

    public Label getWinLabel() {
        return winLabel;
    }

    public Label getTextMessageLabel() {
        return textMessageLabel;
    }

    public void setTotalWin(int totalWin) {
        this.totalWin = totalWin;
        totalWinLabel.setText(Integer.toString(totalWin));
    }

    public Label getBalanceLabel() {
        return balanceLabel;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        balanceLabel.setText(Integer.toString(balance));
    }

    public int getLastWin() {
        return lastWin;
    }

    public void setLastWin(int lastWin) {
        this.lastWin = lastWin;
    }

    public Button getDepositButton() {
        return depositButton;
    }

    public void setDepositButton(Button depositButton) {
        this.depositButton = depositButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    public Label getTotalWinLabel() {
        return totalWinLabel;
    }

    public void setTotalWinLabel(Label totalWinLabel) {
        this.totalWinLabel = totalWinLabel;
    }

    public Label getLastWinLabel() {
        return lastWinLabel;
    }

    public void setLastWinLabel(Label lastWinLabel) {
        this.lastWinLabel = lastWinLabel;
    }

    public Label getBetLabel() {
        return betLabel;
    }

    public void setBetLabel(Label betLabel) {
        this.betLabel = betLabel;
    }

    public Pane getMainPane() {
        return mainPane;
    }

    public ObservableList<Chip> getOwningChips() {
        return owningChips;
    }

    public ArrayList<Chip> getBettingChips() {
        return bettingChips;
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
