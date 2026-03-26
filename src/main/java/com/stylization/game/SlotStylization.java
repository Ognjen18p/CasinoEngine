package com.stylization.game;

import com.basis.game.machine_game.Slot;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;

public class SlotStylization {
    private final Slot slot;

    public SlotStylization(Slot slot) {
        this.slot = slot;
        styleSlot();
    }

    public void styleSlot() {
        styleMainPane();
        styleLabels();
        styleButtons();
    }

    private void styleMainPane() {
        BackgroundFill backgroundFill = new BackgroundFill(
                Color.web("#969696"),
                CornerRadii.EMPTY,
                Insets.EMPTY
        );
        slot.getMainPane().setBackground(new Background(backgroundFill));
    }

    private void styleLabels() {
        String goldLabel =
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.5), 8, 0, 0, 0);";

        String redLabel =
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-text-fill: #CC2200;" +
                        "-fx-effect: dropshadow(gaussian, rgba(200,0,0,0.6), 10, 0, 0, 0);";

        slot.getWinLabel().setStyle(goldLabel);
        slot.getTotalWinLabel().setStyle(goldLabel);
        slot.getBalanceLabel().setStyle(goldLabel);
        slot.getBetLabel().setStyle(redLabel);
        slot.getLastWinLabel().setStyle(goldLabel);
    }

    private void styleButtons() {
        String spinStyle =
                "-fx-background-color: linear-gradient(to bottom, #CC2200, #880000);" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-padding: 18 50 18 50;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(200,0,0,0.7), 15, 0, 0, 4);";

        String spinHover =
                "-fx-background-color: linear-gradient(to bottom, #FF3300, #AA0000);" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-padding: 18 50 18 50;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,50,0,0.9), 20, 0, 0, 4);";

        String betStyle =
                "-fx-background-color: linear-gradient(to bottom, #2A2A2A, #111111);" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-padding: 10 25 10 25;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 6;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.3), 8, 0, 0, 2);";

        String betHover =
                "-fx-background-color: linear-gradient(to bottom, #3A3A3A, #222222);" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-padding: 10 25 10 25;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 6;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.6), 12, 0, 0, 2);";

        String exitDepositStyle =
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #888888;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-padding: 8 20 8 20;" +
                        "-fx-background-radius: 4;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: #444444;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 4;";

        Button spin = slot.getSpinButton();
        spin.setPrefWidth(160);
        spin.setPrefHeight(60);
        spin.setStyle(spinStyle);
        spin.setOnMouseEntered(e -> spin.setStyle(spinHover));
        spin.setOnMouseExited(e -> spin.setStyle(spinStyle));

        Button add = slot.getAddBetButton();
        add.setPrefWidth(55);
        add.setPrefHeight(45);
        add.setStyle(betStyle);
        add.setOnMouseEntered(e -> add.setStyle(betHover));
        add.setOnMouseExited(e -> add.setStyle(betStyle));

        Button remove = slot.getRemoveBetButton();
        remove.setPrefWidth(55);
        remove.setPrefHeight(45);
        remove.setStyle(betStyle);
        remove.setOnMouseEntered(e -> remove.setStyle(betHover));
        remove.setOnMouseExited(e -> remove.setStyle(betStyle));

        slot.getDepositButton().setStyle(exitDepositStyle);
        slot.getExitButton().setStyle(exitDepositStyle);
    }
}