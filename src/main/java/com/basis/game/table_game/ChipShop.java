package com.basis.game.table_game;

import com.application.GameManager;
import com.application.configuration.CasinoConfiguration;
import com.application.utilities.Vector2;
import com.database.person.PlayerDAO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class ChipShop {
    private final HBox mainPane;
    private final TableGame game;
    private final Label instructionLabel;
    private final Button openButton;
    private final PlayerDAO playerDAO;
    private ArrayList<Chip> availableChips;
    private String errorMessage;

    public ChipShop(TableGame game, PlayerDAO playerDAO, Vector2 openButtonPosition, Vector2 instructionLabelPosition) {
        this.game = game;
        this.playerDAO = playerDAO;
        instructionLabel = new Label("Left click to purchase \n Right click to sell");
        instructionLabel.setLayoutX(instructionLabelPosition.getX());
        instructionLabel.setLayoutY(instructionLabelPosition.getY());
        openButton = new Button("Open Chip Shop");
        openButton.setLayoutX(openButtonPosition.getX());
        openButton.setLayoutY(openButtonPosition.getY());
        mainPane = new HBox(10);
        mainPane.setPadding(new Insets(10));
        mainPane.setVisible(false);
        game.getMainPane().getChildren().addAll(mainPane, openButton);
        addShopChipsAndBuyingEvents();
        styleShop();
        shopOpening();
    }

    public PlayerDAO getPlayerDAO() {
        return playerDAO;
    }

    public HBox getMainPane() {
        return mainPane;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Label getInstructionLabel() {
        return instructionLabel;
    }

    public Button getOpenButton() {
        return openButton;
    }

    public ArrayList<Chip> getAvailableChips() {
        return availableChips;
    }

    private void styleShop() {
        openButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFD700, #FFA500);" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;" +
                "-fx-cursor: hand;" +
                "-fx-max-width: 100px;" +
                "-fx-max-height: 80px;" +
                "-fx-padding: 0;"
        );
        mainPane.setStyle("-fx-background-color: rgba(0,0,0,0.75);" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #FFD700;" +
                "-fx-border-radius: 12;" +
                "-fx-border-width: 2;"
        );
    }

    private void shopOpening() {
        openButton.setOnAction(event -> {
            mainPane.setVisible(!mainPane.isVisible());
        });
    }

    private void addShopChipsAndBuyingEvents() {
        availableChips = new ArrayList<>();
        for (int bettingValue : CasinoConfiguration.BETTING_VALUES) {
            Chip nChip = new Chip(bettingValue, 0, 0, 50, true);
            availableChips.add(nChip);
            mainPane.getChildren().add(nChip.getButton());

            nChip.getButton().setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!nChip.isPurchased()) {
                        if (GameManager.getInstance().getCurrentPlayer().getBalance() >= bettingValue) {
                            Chip purchasedChip = new Chip(bettingValue, 0, 0, 50, true);
                            double newBalance = GameManager.getInstance().getCurrentPlayer().getBalance() - bettingValue;
                            GameManager.getInstance().getCurrentPlayer().addChip(bettingValue);
                            GameManager.getInstance().getCurrentPlayer().setBalance(newBalance);
                            if (!playerDAO.purchaseAndSaleChip(-bettingValue))
                                System.out.println(playerDAO.getErrorMessage());
                            game.setBalance(newBalance);
                            game.getMainPane().getChildren().add(purchasedChip.getButton());
                            purchaseChipTransition(purchasedChip);
                            return;
                        }
                        errorMessage = "Not enough credit!";
                    }
                }
            });
        }
    }

    private void purchaseChipTransition(Chip purchasedChip) {
        Chip destinationChip = null;
        for (Chip nChip : game.getOwningChips()) {
            if (nChip.getButton() == null && purchasedChip.getValue() == nChip.getValue()) {
                destinationChip = nChip;
                break;
            }
        }
        if (destinationChip == null) return;

        purchasedChip.getButton().setTranslateX(0);
        purchasedChip.getButton().setTranslateY(0);
        Timeline timelineTransition = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(purchasedChip.getButton().layoutXProperty(), destinationChip.getImage().getLayoutX()),
                        new KeyValue(purchasedChip.getButton().layoutYProperty(), destinationChip.getImage().getLayoutY())
                ));
        timelineTransition.setOnFinished(event -> {
            game.getOwningChips().add(purchasedChip);
            purchasedChip.setPurchased(true);
        });
        timelineTransition.play();
    }

    public void sellChipTransition(Chip sellingChip) {
        if (sellingChip.isPurchased()) {
            if (sellingChip.isSelected())
                game.setBet(game.getBet() - sellingChip.getValue());

            double newBalance = GameManager.getInstance().getCurrentPlayer().getBalance() + sellingChip.getValue();
            GameManager.getInstance().getCurrentPlayer().removeFromOwningChips(sellingChip.getValue());
            GameManager.getInstance().getCurrentPlayer().setBalance(newBalance);
            if (!playerDAO.purchaseAndSaleChip(-sellingChip.getValue()))
                System.out.println(playerDAO.getErrorMessage());
            game.setBalance(newBalance);
            Timeline timelineTransition = new Timeline(new KeyFrame(Duration.millis(500),
                    new KeyValue(sellingChip.getButton().layoutYProperty(), -100)
            ));
            timelineTransition.setOnFinished(event -> {
                sellingChip.getButton().setDisable(true);
                game.getOwningChips().remove(sellingChip);
                game.getMainPane().getChildren().remove(sellingChip.getButton());
            });
            timelineTransition.play();
        }
    }
}