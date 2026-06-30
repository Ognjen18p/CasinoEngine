package com.basis.game.table_game.roulette;

import com.application.GameManager;
import com.application.utilities.Vector2;
import com.basis.game.table_game.ChipShop;
import com.basis.game.table_game.TableGame;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Roulette extends TableGame {

    private ArrayList<Field> bettingFields;
    private Button spinButton;
    private Layout layout;
    private Wheel wheel;

    public Wheel getWheel() {
        return wheel;
    }

    public ArrayList<Field> getBettingFields() {
        return bettingFields;
    }

    public void addBettingField(Field bettingField) {
        bettingFields.add(bettingField);
    }

    public Layout getLayout() {
        return layout;
    }

    public Button getSpinButton() {
        return spinButton;
    }

    public Roulette() {
        initializeElements();
    }

    @Override
    protected void initializeElements() {
        super.initializeElements();

        spinButton = new Button();
        spinButton.setText("Spin");
        spinButton.setLayoutX(200);
        spinButton.setLayoutY(100);
        spinButton.setVisible(false);

        winLabel = new Label("Win: " + win);
        winLabel.setTranslateX(120);
        winLabel.setTranslateY(500);

        totalWinLabel = new Label("Total win value: " + totalWins);
        totalWinLabel.setTranslateX(200);
        totalWinLabel.setTranslateY(250);

        lastWinLabel = new Label("Last win value: " + lastWin);
        lastWinLabel.setTranslateX(300);
        lastWinLabel.setTranslateY(350);

        betLabel = new Label(bet + " BET");
        betLabel.setTranslateX(100);
        betLabel.setTranslateY(350);

        balanceLabel = new Label(GameManager.getInstance().getCurrentPlayer().getBalance() + " BALANCE");
        balanceLabel.setTranslateX(60);
        balanceLabel.setTranslateY(50);

        depositButton.setTranslateX(200);
        depositButton.setTranslateY(50);
        depositButton.setPrefWidth(200);

        exitButton.setTranslateX(700);
        exitButton.setTranslateY(50);
        exitButton.setPrefWidth(200);

        bettingChips = new ArrayList<>();
        bettingFields = new ArrayList<>();
        fillOwningChips(100, 200, 700);

        layout = new Layout(new Vector2(45, 55), new Vector2(300, 400));

        wheel = new Wheel(new Vector2(600, 600));
        wheel.getTable().setLayoutX(100);
        wheel.getTable().setLayoutY(100);
        wheel.getTable().setVisible(false);

        mainPane.getChildren().addAll(wheel.getTable(), spinButton, layout.getMainPane(), depositButton, balanceLabel, winLabel, totalWinLabel, lastWinLabel, exitButton, betLabel);
    }

}
