package com.basis.game;

import com.application.GameManager;
import com.application.configuration.GameSettings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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

    protected int bet;
    protected int lastBet;
    protected int totalWins;
    protected double win;
    protected double lastWin;

    protected boolean isPlaying = false;

    protected void initializeElements() {
        bet = 0;
        lastBet = lastBet;
        totalWins = totalWins;
        lastWin = lastWin;
        win = 0;

        mainPane = new Pane();
        mainPane.setPrefSize(GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());
        depositButton = new Button("Deposit");
        exitButton = new Button("Exit");
        totalWinLabel = new Label("" + totalWins);
        lastWinLabel = new Label("" + lastWin);
        betLabel = new Label("" + bet);
    }

    @Override
    public String toString() {
        return "Game{" +
//                "balance=" + balance +
                ", bet=" + bet +
                ", lastBet=" + lastBet +
                ", totalWins=" + totalWins +
                ", lastWin=" + lastWin +
                '}';
    }

    public void setBet(int bet) {
        betLabel.setText(bet + " BET");
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public int getLastBet() {
        return lastBet;
    }

    public void setLastBet(int lastBet) {
        this.lastBet = lastBet;
    }

    public int getTotalWin() {
        return totalWins;
    }

    public double getWin() {
        return win;
    }

    public void setWin(double win) {
        this.win = win;
        winLabel.setText(win + " WIN");
    }

    public Label getWinLabel() {
        return winLabel;
    }

    public Label getTextMessageLabel() {
        return textMessageLabel;
    }

    public void setTotalWin(int totalWins) {
        this.totalWins = totalWins;
        totalWinLabel.setText(totalWins + " TOTAL WIN");
    }

    public Label getBalanceLabel() {
        return balanceLabel;
    }

    public void setBalance(double balance) {
        balanceLabel.setText(balance + " BALANCE");
    }

    public double getLastWin() {
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
