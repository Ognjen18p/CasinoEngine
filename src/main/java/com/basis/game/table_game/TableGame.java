package com.basis.game.table_game;

import com.application.GameManager;
import com.application.configuration.CasinoConfiguration;
import com.basis.game.Game;
import com.basis.game.table_game.blackjack.BlackJack;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class TableGame extends Game {

    protected ObservableList<Chip> owningChips;
    protected ArrayList<Chip> bettingChips;

    protected TableGame() {
    }

    public ObservableList<Chip> getOwningChips() {
        return owningChips;
    }

    public ArrayList<Chip> getBettingChips() {
        return bettingChips;
    }

    protected void fillOwningChips(double padding, double xPos, double yPos) {
        owningChips = FXCollections.observableArrayList();
        for (int bettingValue : CasinoConfiguration.BETTING_VALUES) {
            Chip emptyChip = new Chip(bettingValue, xPos, yPos, 50, false);
            owningChips.add(emptyChip);
            xPos += padding;
        }
    }

    public void addOwningChips(){
        Map<Integer, Integer> savedChips = GameManager.getInstance().getCurrentPlayer().getOwningChips();
        for (Map.Entry<Integer, Integer> entry : savedChips.entrySet()) {
            int chipValue = entry.getKey();
            int quantity = entry.getValue();

            for (int nChip = 0; nChip < quantity; nChip++) {
                for (Chip chip : FXCollections.observableArrayList(owningChips)) {
                    if (chip.getValue() == chipValue) {
                        Chip savedChip = new Chip(chipValue, chip.getImage().getLayoutX(), chip.getImage().getLayoutY(), chip.getSize(), true);
                        owningChips.add(savedChip);
                        mainPane.getChildren().add(savedChip.getButton());
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void initializeElements() {
        super.initializeElements();
    }
}
