package com.basis.game.machine_game;

import com.application.GameManager;
import com.basis.game.Game;
import com.application.configuration.GameSettings;
import com.application.utilities.Vector2;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Slot extends Game {
    private final int minimumBet;
    private final int maximumBet;
    private final Vector2 slotGridSize;
    private Button spinButton;
    private Button addBetButton;
    private Button removeBetButton;
    private ImageView border;
    private ArrayList<Reel> reels;

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

    public Vector2 getSlotGridSize() {
        return slotGridSize;
    }

    public Slot( Vector2 slotGridSize, int minimumBet, int maximumBet) {
        this.slotGridSize = slotGridSize;
        this.minimumBet = minimumBet;
        this.maximumBet = maximumBet;
        initializeElements();
    }

    @Override
    protected void initializeElements() {
        super.initializeElements();

        spinButton = new Button("Spin");
        spinButton.setLayoutX(850);
        spinButton.setLayoutY(600);

        winLabel = new Label("Win: " + win);
//        winLabel.setTranslateX(120);
//        winLabel.setTranslateY(700);

        totalWinLabel = new Label("Total win value: " + totalWins);
//        totalWinLabel.setTranslateX(300);
//        totalWinLabel.setTranslateY(700);

        lastWinLabel = new Label("Last win value: " + lastWin);
//        lastWinLabel.setTranslateX(300);
//        lastWinLabel.setTranslateY(350);

        betLabel = new Label(bet + " BET");
//        betLabel.setTranslateX(800);
//        betLabel.setTranslateY(500);

        addBetButton = new Button("+");
//        addBetButton.setLayoutX(850);
//        addBetButton.setLayoutY(500);

        removeBetButton = new Button("-");
//        removeBetButton.setLayoutX(750);
//        removeBetButton.setLayoutY(500);

        balanceLabel = new Label(GameManager.getInstance().getCurrentPlayer().getBalance() + " BALANCE");
//        balanceLabel.setTranslateX(850);
//        balanceLabel.setTranslateY(750);

        depositButton.setTranslateX(200);
        depositButton.setTranslateY(100);
        depositButton.setPrefWidth(200);

        exitButton.setTranslateX(700);
        exitButton.setTranslateY(100);
        exitButton.setPrefWidth(200);

        border = new ImageView(new Image(getClass().getResource("/images/SlotImages/border_ninja.png").toExternalForm()));
        border.setFitWidth(GameSettings.getInstance().getWindowWidth());
        border.setFitHeight(GameSettings.getInstance().getWindowHeight());

        makeReels();

        HBox footer = new HBox();
        footer.setLayoutX(0);
        footer.setLayoutY(600);
        footer.getChildren().addAll(balanceLabel, addBetButton, removeBetButton, winLabel, totalWinLabel, lastWinLabel, betLabel, spinButton);

        mainPane.getChildren().addAll(border, depositButton, exitButton, footer);
    }

    private void makeReels() {
        reels = new ArrayList<>();
        for (int nReel = 0; nReel < slotGridSize.getX(); nReel++) {
            Reel reel = new Reel((int) slotGridSize.getY(), 120, 10, new Vector2(200 + nReel * 120, 230));
            reels.add(reel);
            mainPane.getChildren().add(reel.getPane());
        }
    }

}
