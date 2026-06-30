package com.controller.game.table_game;

import com.application.configuration.GameSettings;
import com.basis.game.table_game.Chip;
import com.basis.game.table_game.ChipShop;
import com.basis.game.table_game.roulette.Ball;
import com.basis.game.table_game.roulette.Field;
import com.basis.game.table_game.roulette.Roulette;
import com.application.utilities.Vector2;
import com.stylization.game.RouletteStylization;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import com.application.GameManager;

import java.util.ArrayList;
import java.util.Map;

public class RouletteController extends TableGameController<Roulette> {
    private Ball ball;

    public RouletteController() {
        super();
        initializeScene();
        setupEventHandlers();
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Roulette");
    }

    @Override
    public void onReset() {
        game.setBalance(GameManager.getInstance().getCurrentPlayer().getBalance());
    }

    @Override
    protected void initializeScene() {
        game = new Roulette();

        chipShop = new ChipShop(game, playerDAO, new Vector2(600, 80), new Vector2(100, 300));

        ball = new Ball(game, new Vector2(22, 22));

        scene = new Scene(game.getMainPane(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());
        new RouletteStylization(game);

    }

    @Override
    protected void setupEventHandlers() {
        handleExit(game);
        handleDeposit(game);
        handleSpin();
        handleBallStopped();
        purchasedChipsListener();
    }

    private void handleSpin() {
        game.getSpinButton().setOnAction(event -> {

            game.getWheel().getTable().setVisible(true);
            game.getWheel().getTable().toFront();
            game.getSpinButton().setVisible(false);
            ball.startBallSpinning();
        });
    }

    private void handleBallStopped() {
        ball.setOnStopped(() -> {
            checkWinner(snapBallToPocket());
        });
    }

    @Override
    protected void purchasedChipsListener() {
        game.getOwningChips().addListener((ListChangeListener<Chip>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Chip chip : change.getAddedSubList()) {
                        if (chip.getButton() != null) {
                            chip.getButton().setOnMouseClicked(event -> {
                                if (event.getButton() == MouseButton.SECONDARY) {
                                    chipShop.sellChipTransition(chip);
                                }
                            });
                            chip.getButton().setOnMousePressed(event -> {
                                removeFieldChip(chip);
                                chip.getButton().setTranslateX(0);
                                chip.getButton().setTranslateY(0);
                            });
                            chip.getButton().setOnMouseDragged(event -> {
                                chip.setDragged(true);
                                chip.getButton().setLayoutX(event.getSceneX() - chip.getButton().getWidth() / 2);
                                chip.getButton().setLayoutY(event.getSceneY() - chip.getButton().getHeight() / 2);
                            });

                            chip.getButton().setOnMouseReleased(event -> {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (chip.isDragged()) {
                                        placeDragged(chip);
                                        chip.setDragged(false);
                                    } else {
                                        onChipSelected(chip);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
        game.addOwningChips();
        debugBettingState();
    }

    private void debugBettingState() {
        System.out.println("=== BETTING STATE ===");
        System.out.println("Total bet: " + game.getBet());
        System.out.println("Betting fields: " + game.getBettingFields().size());
        for (Field field : game.getBettingFields()) {
            System.out.println("  Field: " + field.getFieldType() +
                    " | Multiplier: " + field.getWinMultiplier() +
                    " | WinValues: " + field.getCoveredNumbers());
            for (Chip chip : field.getCoveredChips()) {
                System.out.println("    Chip ref: " + chip.hashCode() +
                        " | Value: " + chip.getValue() +
                        " | Selected: " + chip.isSelected());
            }
        }
        System.out.println("Betting chips total: " + game.getBettingChips().size());
        for (Chip chip : game.getBettingChips()) {
            System.out.println("  BetChip ref: " + chip.hashCode() +
                    " | Value: " + chip.getValue());
        }
        System.out.println("====================");
    }

    @Override
    protected void onChipSelected(Chip chip) {
        Chip destinationChip = null;
        for (Chip nChip : game.getOwningChips()) {
            if (nChip.getButton() == null && nChip.getValue() == chip.getValue()) {
                destinationChip = nChip;
                break;
            }
        }

        if (destinationChip != null) {
            Timeline transition = new Timeline(
                    new KeyFrame(Duration.millis(300),
                            new KeyValue(chip.getButton().layoutXProperty(), destinationChip.getImage().getLayoutX()),
                            new KeyValue(chip.getButton().layoutYProperty(), destinationChip.getImage().getLayoutY())
                    ));
            game.setBet(Math.max(0, game.getBet() - chip.getValue()));
            game.getBettingChips().remove(chip);
            game.getOwningChips().add(chip);
            game.getSpinButton().setVisible(game.getBet() > 0);
            removeFieldChip(chip);
            chip.getButton().setDisable(true);
            transition.setOnFinished(e -> {
                chip.setSelected(false);
                chip.getButton().setDisable(false);
            });
            transition.play();
        }
        debugBettingState();
    }

    private void removeFieldChip(Chip chip) {
        if (game.getBettingFields().isEmpty()) return;
        for (Field field : new ArrayList<>(game.getBettingFields())) {
            if (field == null || field.getCoveredChips().isEmpty()) continue;
            for (Chip nChip : new ArrayList<>(field.getCoveredChips())) {
                if (nChip.equals(chip)) {
                    field.getCoveredChips().remove(nChip);
                    game.getBettingFields().remove(field);
                    break;
                }
            }
        }
    }

    private void placeDragged(Chip chip) {
        double closest = 99999;
        Field closestField = null;
        Vector2 closestFieldPosition = new Vector2();
        for (Field field : game.getLayout().getLayoutFields()) {
            Bounds fieldBounds = field.getButton().localToScene(field.getButton().getBoundsInLocal());
            Vector2 fieldPosition = new Vector2(fieldBounds.getMinX() + fieldBounds.getWidth() / 2,
                    fieldBounds.getMinY() + fieldBounds.getHeight() / 2);
            Vector2 chipPosition = new Vector2(chip.getButton().getLayoutX() + chip.getButton().getWidth() / 2,
                    chip.getButton().getLayoutY() + chip.getButton().getHeight() / 2);
            double distance = Vector2.distance(chipPosition, fieldPosition);
            if (distance < closest) {
                closest = distance;
                closestFieldPosition.setX(fieldPosition.getX());
                closestFieldPosition.setY(fieldPosition.getY());
                closestField = field;
            }
        }
        if (closest < 50) {
            chip.getButton().setLayoutX(closestFieldPosition.getX() - chip.getButton().getWidth() / 2);
            chip.getButton().setLayoutY(closestFieldPosition.getY() - chip.getButton().getHeight() / 2);

            if (!closestField.getCoveredChips().contains(chip)) {
                closestField.getCoveredChips().add(chip);
                game.addBettingField(closestField);
                if (!chip.isSelected()) {
                    game.setBet(game.getBet() + chip.getValue());
                    game.getBettingChips().add(chip);
                    game.getOwningChips().remove(chip);
                    chip.setSelected(true);
                }
            }
            game.getSpinButton().setVisible(true);
        } else
            onChipSelected(chip);
        debugBettingState();

    }

    public int snapBallToPocket() {
        int closestPocket = -1;
        double closestDistance = 99999;
        for (Map.Entry<Integer, ImageView> entry : game.getWheel().getPockets().entrySet()) {
            Bounds bounds = entry.getValue().localToScene(entry.getValue().getBoundsInLocal());
            Vector2 pocketPosition = new Vector2(bounds.getCenterX(), bounds.getCenterY());

            Bounds ballBounds = ball.getImage().localToScene(ball.getImage().getBoundsInLocal());
            Vector2 ballPosition = new Vector2(ballBounds.getCenterX(), ballBounds.getCenterY());
            double distance = ballPosition.distance(pocketPosition);

            if (distance < closestDistance) {
                closestPocket = entry.getKey();
                closestDistance = distance;
            }
        }
        System.out.println(closestPocket);
        ball.getImage().setX(game.getWheel().getPockets().get(closestPocket).getX());
        ball.getImage().setY(game.getWheel().getPockets().get(closestPocket).getY());
        return closestPocket;
    }

    private Transition clearBet() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ArrayList<Chip> removeChips = new ArrayList<>();
        for (Chip nChip : game.getBettingChips()) {
            if (nChip.getButton() != null) {
                Timeline transition = new Timeline(
                        new KeyFrame(Duration.millis(300),
                                new KeyValue(nChip.getButton().layoutYProperty(), -100)
                        ));
                sequentialTransition.getChildren().add(transition);
                removeChips.add(nChip);
            }
        }
        sequentialTransition.setOnFinished(event -> {
            for (Chip rChip : removeChips) {
                game.getBettingChips().remove(rChip);
                removeFieldChip(rChip);
                game.getMainPane().getChildren().remove(rChip.getButton());
            }
        });
        return sequentialTransition;
    }

    private void checkWinner(int number) {
        boolean win = false;
        double winningAmount = 0;
        for (Field field : game.getBettingFields()) {
            for (int winValue : field.getCoveredNumbers()) {
                if (winValue == number) {
                    winningAmount += field.getWinMultiplier();
                    win = true;
                    break;
                }
            }
        }
        if (win) {
            game.setWin(game.getBet() * winningAmount);
            takeWinningChips().play();
            System.out.println("winner ");
            for (Chip chip : new ArrayList<>(game.getBettingChips()))
                onChipSelected(chip);
        } else {
            System.out.println("loser");
            game.setWin(0);
            clearBet().play();
        }
        System.out.println(winningAmount + " win amount ");
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(1500));
        pauseTransition.setOnFinished(event -> {
            game.getWheel().getTable().setVisible(false);
            game.setBet(0);
        });
        pauseTransition.play();
    }
}