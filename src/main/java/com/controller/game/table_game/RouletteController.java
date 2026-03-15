package com.controller.game.table_game;

import com.basis.game.table_game.TableGame;
import com.basis.game.table_game.blackjack.Chip;
import com.basis.game.table_game.ChipShop;
import com.basis.game.table_game.roulette.Ball;
import com.basis.game.table_game.roulette.Field;
import com.basis.game.table_game.roulette.Roulette;
import com.basis.game.essentials.Vector2;
import com.controller.game.TableGameController;
import com.stylization.game.RouletteStylization;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.GameManager;

import java.util.ArrayList;
import java.util.Map;

public class RouletteController extends TableGameController {
    private final Roulette roulette;
    private Ball ball;
    private ChipShop chipShop;

    public RouletteController(GameManager gameManager) {
        this.gameManager = gameManager;
        roulette = new Roulette();
        initializeScene();
        chipShop = new ChipShop(roulette);
        setupEventHandlers();
    }

    @Override
    public void showScene() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("Roulette");
    }

    @Override
    protected void initializeScene() {
        ball = new Ball(roulette, new Vector2(22, 22));

        chipShop = new ChipShop(roulette);

        scene = new Scene(roulette.getMainPane(), 1000, 800);
        RouletteStylization stylization = new RouletteStylization(roulette);

    }

    @Override
    protected void setupEventHandlers() {
        handleSpin();
        handleBallStopped();
        handleChipShop();
        purchasedChip(roulette);
    }

    private void handleSpin() {
        roulette.getSpinButton().setOnAction(event -> {
            roulette.getWheel().getTable().setVisible(true);
            roulette.getWheel().getTable().toFront();
            roulette.getSpinButton().setVisible(false);
            ball.startBallMovement();
        });
    }

    private void handleBallStopped(){
        ball.setOnStopped(() ->{
            checkWinState(snapBallToPocket());
        });
    }

    private void handleChipShop() {
        roulette.getChipShopButton().setOnAction(actionEvent ->
                chipShop.getMainPane().setVisible(!chipShop.getMainPane().isVisible()));
    }

    @Override
    protected void purchasedChip(TableGame game) {
        game.getOwningChips().addListener((ListChangeListener<Chip>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Chip chip : change.getAddedSubList()) {
                        if (chip.getButton() != null) {
                            chip.getButton().setOnMousePressed(event -> {
                                chip.getButton().setTranslateX(0);
                                chip.getButton().setTranslateY(0);
                            });
                            chip.getButton().setOnMouseDragged(event -> {
                                chip.setDragged(true);
                                chip.getButton().setLayoutX(event.getSceneX() - chip.getButton().getWidth() / 2);
                                chip.getButton().setLayoutY(event.getSceneY() - chip.getButton().getHeight() / 2);
                            });

                            chip.getButton().setOnMouseReleased(event -> {
                                if (chip.isDragged()) {
                                    placeDragged(chip);
                                } else
                                    selectingChip(chip);
                                chip.setDragged(false);
                            });
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void selectingChip(Chip chip) {
        Chip destinationChip = null;
        for (Chip nChip : roulette.getOwningChips()) {
            if (nChip.getButton() == null && nChip.getValue() == chip.getValue()) {
                destinationChip = nChip;
                break;
            }
        }
        Timeline transition = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(chip.getButton().layoutXProperty(), destinationChip.getImage().getX()),
                        new KeyValue(chip.getButton().layoutYProperty(), destinationChip.getImage().getY())
                ));
        roulette.setBet(Math.max(0, roulette.getBet() - chip.getValue()));
        roulette.getBettingChips().remove(chip);
        roulette.getOwningChips().add(chip);
        roulette.getSpinButton().setVisible(roulette.getBet() > 0);
        chip.getButton().setDisable(true);
        transition.setOnFinished(e -> {
            chip.setSelected(false);
            chip.getButton().setDisable(false);
        });
        transition.play();
    }

    private void placeDragged(Chip chip) {
        double closest = 9999;
        Field closestField = null;
        Vector2 closestFieldPosition = new Vector2();
        for (Field field : roulette.getLayout().getLayoutFields()) {
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
            roulette.setBettingField(closestField);
            roulette.getSpinButton().setVisible(true);
            if(!chip.isSelected()) {
                roulette.setBet(roulette.getBet() + chip.getValue());
                roulette.getBettingChips().add(chip);
                roulette.getOwningChips().remove(chip);
                chip.setSelected(true);
            }
        } else
            selectingChip(chip);
    }

    private Transition clearBet() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ArrayList<Chip> removeChips = new ArrayList<>();
        for (Chip nChip : roulette.getBettingChips()) {
            if (nChip.getButton() != null) {
                TranslateTransition moveChip = new TranslateTransition(Duration.millis(1100), nChip.getButton());
                moveChip.setByY((-100) - nChip.getButton().getLayoutY());
                sequentialTransition.getChildren().add(moveChip);
                removeChips.add(nChip);
            }
        }
        sequentialTransition.setOnFinished(event -> {
            for (Chip rChip : removeChips) {
                roulette.getBettingChips().remove(rChip);
                roulette.getMainPane().getChildren().remove(rChip.getButton());
            }
        });
        return sequentialTransition;
    }

    public int snapBallToPocket() {
        int closestPocket = -1;
        double closestDistance = 99999;
        for (Map.Entry<Integer, ImageView> entry : roulette.getWheel().getPockets().entrySet()) {
            Bounds bounds = entry.getValue().localToScene(entry.getValue().getBoundsInLocal());
            Vector2 pocketPosition = new Vector2(bounds.getCenterX(), bounds.getCenterY());

            Bounds ballBounds = ball.getImage().localToScene(ball.getImage().getBoundsInLocal());
            Vector2 ballPosition = new Vector2(ballBounds.getCenterX(),ballBounds.getCenterY());
            double distance = ballPosition.distance(pocketPosition);

            if (distance < closestDistance) {
                closestPocket = entry.getKey();
                closestDistance = distance;
                System.out.println(ballPosition + " ball pos ");
                System.out.println(pocketPosition + " pocekt pos ");

            }
        }

        ball.getImage().setX(roulette.getWheel().getPockets().get(closestPocket).getX());
        ball.getImage().setY(roulette.getWheel().getPockets().get(closestPocket).getY());
        return closestPocket;
    }

    private void checkWinState(int number){
        boolean win = false;
        for(int value : roulette.getBettingField().getWinValues()){
            if (value == number) {
                win = true;
                break;
            }
        }
        if(win) {
            roulette.setWin(roulette.getWin() + roulette.getBet() * roulette.getBettingField().getWinMultiplier());
            roulette.setBalance(roulette.getBalance() + roulette.getBet() * roulette.getBettingField().getWinMultiplier());
            for(Chip chip : new ArrayList<>(roulette.getBettingChips()))
                selectingChip(chip);
        }
        else {
            System.out.println(" DEALER WINS! ");
            roulette.setWin(0);
            clearBet().play();
        }
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(3000));
        pauseTransition.setOnFinished(event -> {
            roulette.getWheel().getTable().setVisible(false);
            roulette.setBet(0);
        });
        pauseTransition.play();
    }
}