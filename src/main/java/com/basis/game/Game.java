package com.basis.game;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class Game {

    protected Pane mainPane;
    protected Button depositButton;
    protected Button exitButton;
    protected Label totalWinLabel;
    protected Label lastWinLabel;

    protected int bet;
    protected int lastBet;
    protected int totalWin;
    protected int lastWin;

    protected boolean isPlaying = false;

    protected void initializeElements() {
        bet = 0;
        lastBet = lastBet;
        totalWin = totalWin;
        lastWin = lastWin;

        mainPane = new Pane();
        depositButton = new Button("Deposit");
        exitButton = new Button("Exit");
        totalWinLabel = new Label(Integer.toString(totalWin));
        lastWinLabel = new Label(Integer.toString(lastWin));

        mainPane.getChildren().addAll(depositButton, exitButton,  totalWinLabel, lastWinLabel);
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

    public Pane getMainPane() {
        return mainPane;
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
