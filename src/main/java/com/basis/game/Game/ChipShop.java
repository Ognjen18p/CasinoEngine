package com.basis.game.Game;

import com.basis.game.BlackJack.Chip;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class ChipShop {
    private Game game;
    private HBox pane;
    private ArrayList<Chip> availableChips;
    private ObservableList<Chip> destinationChips;

    public ChipShop(Game game, ObservableList<Chip> destinationChips) {
        this.game = game;
        this.destinationChips = destinationChips;
        pane = new HBox(10);
        pane.setPadding(new Insets(10));
        pane.setStyle(
                "-fx-background-color: rgba(0,0,0,0.75);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-width: 2;"
        );

        fillShopChipsAndEvents();
    }

    public HBox getPane() {
        return pane;
    }

    private String shopButtonStyle() {
        return "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFA500);" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;" +
                "-fx-cursor: hand;" +
                "-fx-min-width: 24px;" +
                "-fx-min-height: 24px;" +
                "-fx-max-width: 24px;" +
                "-fx-max-height: 24px;" +
                "-fx-padding: 0;";
    }

    private void fillShopChipsAndEvents() {
        availableChips = new ArrayList<>();
        for (int bettingValue : Chip.CHIP_VALUES) {
            VBox boxPane = new VBox(4);
            boxPane.setPadding(new Insets(6));
            boxPane.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.08);" +
                            "-fx-background-radius: 8;"
            );
            boxPane.setAlignment(javafx.geometry.Pos.CENTER);

            Label valueLabel = new Label(bettingValue + "");
            valueLabel.setStyle(
                    "-fx-text-fill: #FFD700;" +
                            "-fx-font-size: 11px;" +
                            "-fx-font-weight: bold;"
            );

            Chip nChip = new Chip(bettingValue);
            nChip.getImage().setFitWidth(40);
            nChip.getImage().setFitHeight(40);
            availableChips.add(nChip);

            HBox buttons = new HBox(6);
            buttons.setAlignment(javafx.geometry.Pos.CENTER);

            Button minus = new Button("-");
            minus.setStyle(shopButtonStyle());

            Button plus = new Button("+");
            plus.setStyle(shopButtonStyle());

            plus.setOnMouseClicked(event -> {
                if (game.getBalance() > bettingValue) {
                    Chip newChip = new Chip(bettingValue, 0, 0, true);
                    game.getMainPane().getChildren().add(newChip.getButton());
                    game.setBalance(game.getBalance() - bettingValue);
                    addChipTransition(newChip);
                }
            });

            minus.setOnMouseClicked(event -> {
                for (Chip chip : destinationChips) {
                    if (chip.getValue() == bettingValue && chip.getButton() != null) {
                        removeChipTransition(chip);
                        game.setBalance(game.getBalance() + bettingValue);
                        break;
                    }
                }
            });

            buttons.getChildren().addAll(minus, plus);
            boxPane.getChildren().addAll(valueLabel, nChip.getImage(), buttons);
            pane.getChildren().add(boxPane);
        }
    }

    private void addChipTransition(Chip chip) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), chip.getButton());
        Chip destinationChip = null;
        for (Chip nChip : destinationChips) {
            if (nChip.getButton() == null && chip.getValue() == nChip.getValue()) {
                destinationChip = nChip;
                break;
            }
        }
        if (destinationChip == null) return;
        chip.getButton().setTranslateX(0);
        chip.getButton().setTranslateY(0);
        transition.setByX(destinationChip.getImage().getX() - chip.getButton().getLayoutX());
        transition.setByY(destinationChip.getImage().getY() - chip.getButton().getLayoutY());
        transition.setOnFinished(event -> destinationChips.add(chip));
        transition.play();
    }

    private void removeChipTransition(Chip chip) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), chip.getButton());
        if (chip.getButton() == null) return;
        chip.getButton().setTranslateX(0);
        chip.getButton().setTranslateY(0);
        transition.setByX(pane.getLayoutX() - chip.getButton().getLayoutX());
        transition.setByY(pane.getLayoutY() - chip.getButton().getLayoutY());
        transition.setOnFinished(event -> {
            destinationChips.remove(chip);
            game.getMainPane().getChildren().remove(chip.getButton());
        });
        transition.play();
    }
}