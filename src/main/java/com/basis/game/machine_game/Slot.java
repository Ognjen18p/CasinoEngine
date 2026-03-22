package com.basis.game.machine_game;

import com.basis.game.Game;
import com.basis.game.essentials.Vector2;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class Slot extends Game {
    private final int minimumBet;
    private final int maximumBet;
    private final Vector2 slotGridSize;
    private Button spinButton;
    private Button addBetButton;
    private Button removeBetButton;
    private ImageView border;
    private ArrayList<Reel> reels;

    private Pane footer;

    public Button getSpinButton() {
        return spinButton;
    }

    public Button getRemoveBetButton() {
        return removeBetButton;
    }

    public Button getAddBetButton() {
        return addBetButton;
    }

    public int getMinimumBet() {
        return minimumBet;
    }

    public int getMaximumBet() {
        return maximumBet;
    }

    public ArrayList<Reel> getReels() {
        return reels;
    }

    public Slot(Vector2 windowSize, Vector2 slotGridSize, int minimumBet, int maximumBet) {
        this.windowSize = windowSize;
        this.slotGridSize = slotGridSize;
        this.minimumBet = minimumBet;
        this.maximumBet = maximumBet;
        initializeElements();
    }

    @Override
    protected void initializeElements() {
        super.initializeElements();

        spinButton = new Button("Spin");
        spinButton.setLayoutX(950);
        spinButton.setLayoutY(600);

        winLabel = new Label("Win: " + win);
        winLabel.setTranslateX(120);
        winLabel.setTranslateY(700);

        totalWinLabel = new Label("Total win value: " + totalWin);
        totalWinLabel.setTranslateX(300);
        totalWinLabel.setTranslateY(700);

        lastWinLabel = new Label("Last win value: " + lastWin);
        lastWinLabel.setTranslateX(300);
        lastWinLabel.setTranslateY(350);

        betLabel = new Label(bet + " BET");
        betLabel.setTranslateX(900);
        betLabel.setTranslateY(500);

        addBetButton = new Button("+");
        addBetButton.setLayoutX(750);
        addBetButton.setLayoutY(500);

        removeBetButton = new Button("-");
        removeBetButton.setLayoutX(850);
        removeBetButton.setLayoutY(500);

        balanceLabel = new Label(balance + " BALANCE");
        balanceLabel.setTranslateX(950);
        balanceLabel.setTranslateY(750);

        depositButton.setTranslateX(100);
        depositButton.setTranslateY(100);

        exitButton.setTranslateX(700);
        exitButton.setTranslateY(100);

        border = new ImageView(new Image(getClass().getResource("/images/SlotImages/Border.png").toExternalForm(), 900, 680, false, true));

        footer = new Pane();
        footer.setLayoutX(0);
        footer.setLayoutY(700);
        footer.setPrefSize(windowSize.getX(), 200);
        footer.getChildren().addAll();

        makeReels();

        mainPane.getChildren().addAll(depositButton, exitButton, balanceLabel, addBetButton, removeBetButton, winLabel, totalWinLabel, lastWinLabel, betLabel, spinButton, border);
    }

    private void makeReels() {
        reels = new ArrayList<>();
        for (int nReel = 0; nReel < slotGridSize.getX(); nReel++) {
            Reel reel = new Reel((int) slotGridSize.getY(), 100, 10, new Vector2(nReel * 110, 100));
            reels.add(reel);
            mainPane.getChildren().add(reel.getPane());
        }
    }

    public void checkWin() {
        int[] connectedCounter = new int[(int) slotGridSize.getY()];
        Arrays.fill(connectedCounter, 1);
        boolean[] connected = new boolean[(int) slotGridSize.getY()];

        for (int nReel = 0; nReel < reels.size() - 1; nReel++) {
            for (int nSymbol = 0; nSymbol < reels.get(nReel).getWinningSymbols().size(); nSymbol++) {
                if (connected[nSymbol]) continue;
                boolean areConnected = reels.get(nReel).getWinningSymbols().get(nSymbol).getSymbolInfo().getValue() == reels.get(nReel + 1).getWinningSymbols().get(nSymbol).getSymbolInfo().getValue();
                if (connectedCounter[nSymbol] >= 3) {
                    if (areConnected)
                        connectedCounter[nSymbol]++;
                    else connected[nSymbol] = true;
                } else connectedCounter[nSymbol] = areConnected ? connectedCounter[nSymbol] + 1 : 1;
            }
        }

        int winMultiplier = 0;
        for (int count = 0; count < connectedCounter.length; count++) {
            switch (connectedCounter[count]) {
                case 3 -> winMultiplier += 5;
                case 4 -> winMultiplier += 10;
                case 5 -> winMultiplier += 50;
            }
        }
        setBalance(balance + bet * winMultiplier);
    }

}
