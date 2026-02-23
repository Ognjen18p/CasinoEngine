package com.stylization.game;

import com.basis.game.BlackJack.BlackJack;
import com.basis.game.BlackJack.Chip;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BlackJackStylization {
    private BlackJack blackJack;

    public BlackJackStylization(BlackJack blackJack) {
        this.blackJack = blackJack;
        styleBlackJack();
    }

    public void styleBlackJack() {
        styleMainPane();
        styleLabels();
        styleButtons();
    }

    private void styleMainPane() {
        // Dark green casino table background
        BackgroundFill backgroundFill = new BackgroundFill(
                Color.web("#0B5E3A"), // Casino green
                CornerRadii.EMPTY,
                Insets.EMPTY
        );
        BackgroundFill backgroundFillChipShop = new BackgroundFill(
                Color.web("#a4cfde"), // Casino green
                CornerRadii.EMPTY,
                Insets.EMPTY
        );
        blackJack.getMainPane().setBackground(new Background(backgroundFill));
    }

    private void styleLabels() {
        String style = "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FFD700;" + // Gold color
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 2);";
        blackJack.getPlaceYourBetsLabel().setStyle(style);
        blackJack.getPlayerScoreLabel().setStyle(style);
        blackJack.getDealerScoreLabel().setStyle(style);
        blackJack.getLastWinLabel().setStyle(style);
        blackJack.getTotalWinLabel().setStyle(style);
        blackJack.getBetLabel().setStyle(style);
        blackJack.getBalanceLabel().setStyle(style);
    }

    private void styleButtons() {
        // Common button style
        String buttonStyle =
                "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFA500);" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 15 40 15 40;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0, 0, 3);";

        String buttonHoverStyle =
                "-fx-background-color: linear-gradient(to bottom, #FFA500, #FF8C00);";

        // Deal button styling
        Button dealButton = blackJack.getDealButton();
        dealButton.setPrefWidth(100);
        dealButton.setPrefHeight(50);
        dealButton.setStyle(buttonStyle);
        dealButton.setOnMouseEntered(e -> dealButton.setStyle(buttonStyle + buttonHoverStyle));
        dealButton.setOnMouseExited(e -> dealButton.setStyle(buttonStyle));

        // Hit button styling
        Button hitButton = blackJack.getHitButton();
        hitButton.setPrefWidth(90);
        hitButton.setPrefHeight(50);
        hitButton.setStyle(buttonStyle);
        hitButton.setVisible(false); // Hidden until game starts
        hitButton.setOnMouseEntered(e -> hitButton.setStyle(buttonStyle + buttonHoverStyle));
        hitButton.setOnMouseExited(e -> hitButton.setStyle(buttonStyle));

        // Stand button styling
        Button standButton = blackJack.getStandButton();
        standButton.setPrefWidth(90);
        standButton.setPrefHeight(50);
        standButton.setStyle(buttonStyle);
        standButton.setVisible(false); // Hidden until game starts
        standButton.setOnMouseEntered(e -> standButton.setStyle(buttonStyle + buttonHoverStyle));
        standButton.setOnMouseExited(e -> standButton.setStyle(buttonStyle));

        blackJack.getChipShopButton().setStyle(buttonStyle);
        blackJack.getChipShopButton().setPrefWidth(60);
        blackJack.getChipShopButton().setPrefHeight(40);

    }
}
