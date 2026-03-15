package com.basis.game.table_game;

import com.basis.game.Game;
import com.basis.game.table_game.blackjack.Chip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.ArrayList;

public abstract class TableGame extends Game {
    protected Button chipShopButton;
    protected ObservableList<Chip> owningChips;
    protected ArrayList<Chip> bettingChips;

    public ObservableList<Chip> getOwningChips() {
        return owningChips;
    }

    public ArrayList<Chip> getBettingChips() {
        return bettingChips;
    }

    protected void fillChipSlots(double padding, double xPos, double yPos) {
        owningChips = FXCollections.observableArrayList();
        for (int bettingValue : Chip.CHIP_VALUES) {
            Chip emptyChip = new Chip(bettingValue, xPos, yPos, false);
            owningChips.add(emptyChip);
            xPos += padding;
        }
    }

    @Override
    protected void initializeElements() {
        super.initializeElements();
    }
}
