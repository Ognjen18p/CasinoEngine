package com.controller.game;

import com.basis.game.BlackJack.BlackJack;
import com.basis.game.BlackJack.Card;
import com.basis.game.BlackJack.Chip;
import com.controller.Controller;
import com.stylization.game.BlackJackStylization;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import main.GameManager;

import java.util.concurrent.ThreadLocalRandom;

public class BlackJackController extends Controller {
    private static final int BLACKJACK = 21;
    private BlackJack blackJack;

    public BlackJackController(GameManager gameManager) {
        this.gameManager = gameManager;
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        handleDeal();
        handleHit();
        handleStand();
        chipsListener();
    }

    @Override
    public void showScene() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("BLACKJACK");
    }

    @Override
    protected void initializeScene() {
        blackJack = new BlackJack();
        shuffle();
        scene = new Scene(blackJack.getMainPane(), 1000, 800);
        BlackJackStylization blackJackStylization = new BlackJackStylization(blackJack);
    }

    private void shuffle() {
        if (blackJack.getDeck() != null && !blackJack.getDeck().isEmpty())
            blackJack.getDeck().clear();

        blackJack.fillDeck();

        for (int nCard = blackJack.getDeck().size() - 1; nCard > 0; nCard--) {
            int random_card = ThreadLocalRandom.current().nextInt(0, blackJack.getDeck().size() - 1);
            blackJack.getDeck().set(nCard, blackJack.getDeck().get(random_card));
        }
    }

    private void chipsListener() {
        for (int i = 0; i < blackJack.getBettingChips().size(); i++) {
            int currentChip = i;
            blackJack.getBettingChips().get(currentChip).getButton().setOnAction(actionEvent -> selectedChip(blackJack.getBettingChips().get(currentChip)));
        }
    }

    private void selectedChip(Chip chip) {
        if (!blackJack.isPlaying()) {
            switch (chip.getValue()) {
                case 10: {
                    blackJack.setBet(blackJack.getBet() + 10);
                    break;
                }
                case 25: {
                    blackJack.setBet(blackJack.getBet() + 25);
                    break;
                }
                case 50: {
                    blackJack.setBet(blackJack.getBet() + 50);
                    break;
                }
                case 100: {
                    blackJack.setBet(blackJack.getBet() + 100);
                    break;
                }
                case 500: {
                    blackJack.setBet(blackJack.getBet() + 500);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    private void handleDeal() {
        blackJack.getDeal().setOnAction(actionEvent -> {
            buttonsActive(true);
            dealCards();
        });
    }

    private void handleHit() {
        blackJack.getHit().setOnAction(actionEvent -> {
            hit();
        });
    }

    private void handleStand() {
        blackJack.getStand().setOnAction(actionEvent -> {
            stand();
        });
    }

    private void takeCard(boolean flipped, Card card, Card destination, Runnable next) {
        card.getImage().toFront();
        card.getImage().setTranslateX(0);
        card.getImage().setTranslateY(0);
        TranslateTransition moveCard = new TranslateTransition(Duration.millis(1200), card.getImage());
        moveCard.setByX(destination.getImage().getX() - card.getImage().getX());
        moveCard.setByY(destination.getImage().getY() - card.getImage().getY());

        RotateTransition rotateCard = new RotateTransition(Duration.millis(1200), card.getImage());
        rotateCard.setByAngle(0);
        rotateCard.setAutoReverse(true);

        if (flipped) {
            Image savedImage = card.getImage().getImage();
            card.getImage().setImage(blackJack.getDealersFlippedCard().getImage().getImage());
            blackJack.getDealersFlippedCard().getImage().setImage(savedImage);
        }

        ParallelTransition parallelTransition = new ParallelTransition(moveCard, rotateCard);
        parallelTransition.setOnFinished(event -> {
            destination.getImage().setImage(card.getImage().getImage());
            destination.setRank(card.getRank());
            destination.setSuit(card.getSuit());
            destination.getImage().toFront();
            card.getImage().setVisible(false);
            next.run();
        });
        parallelTransition.play();
    }

    private int getPlayerFreeSlot() {
        int currentFreeSlot = blackJack.getPlayerFreeSlot();
        blackJack.setPlayerFreeSlot(blackJack.getPlayerFreeSlot() + 1);
        return currentFreeSlot;
    }

    private int getDealerFreeSlot() {
        int currentFreeSlot = blackJack.getDealerFreeSlot();
        blackJack.setDealerFreeSlot(blackJack.getDealerFreeSlot() + 1);
        return currentFreeSlot;
    }

    private int getDecksFirst() {
        int first = blackJack.getFirstInDeck();
        blackJack.setFirstInDeck(blackJack.getFirstInDeck() + 1);
        return first;
    }

    private void dealCards() {
        takeCard(false, blackJack.getDeck().get(getDecksFirst()), blackJack.getPlayerSlots().get(getPlayerFreeSlot()), () ->
                takeCard(true, blackJack.getDeck().get(getDecksFirst()), blackJack.getDealerSlots().get(getDealerFreeSlot()), () ->
                        takeCard(false, blackJack.getDeck().get(getDecksFirst()), blackJack.getPlayerSlots().get(getPlayerFreeSlot()), () ->
                                takeCard(false, blackJack.getDeck().get(getDecksFirst()), blackJack.getDealerSlots().get(getDealerFreeSlot()), this::calculateScore)
                        )
                )
        );
    }

    private void calculateScore() {
        for (Card card : blackJack.getPlayerSlots()) {
            if (card.getRank() != null)
                blackJack.setPlayerScore(blackJack.getPlayerScore() + card.getRank().getValue());
        }
        System.out.println(blackJack.getPlayerScore());
        for (Card card : blackJack.getDealerSlots()) {
            if (card.getRank() != null)
                blackJack.setDealerScore(blackJack.getDealerScore() + card.getRank().getValue());
        }
        System.out.println(blackJack.getDealerScore());
    }

    private void buttonsActive(boolean active) {
        blackJack.getHit().setVisible(active);
        blackJack.getStand().setVisible(active);
    }

    private void hit() {
        checkPlayerOverMax();
    }

    private void revealFlipped() {
        SequentialTransition flipTransition = new SequentialTransition();

        ScaleTransition scaleTo0 = new ScaleTransition(Duration.millis(500), blackJack.getDealerSlots().getFirst().getImage());
        scaleTo0.setToX(0);
        scaleTo0.setOnFinished(event -> {
            blackJack.getDealerSlots().getFirst().getImage().setImage(blackJack.getDealersFlippedCard().getImage().getImage());
        });

        RotateTransition rotate = new RotateTransition(Duration.millis(0), blackJack.getDealerSlots().getFirst().getImage());
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(180);
        rotate.setAutoReverse(true);

        ScaleTransition scaleBackTo1 = new ScaleTransition(Duration.millis(500), blackJack.getDealerSlots().getFirst().getImage());
        scaleBackTo1.setToX(1);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), blackJack.getDealerSlots().getFirst().getImage());
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setByAngle(180);
        rotateTransition.setAutoReverse(true);

        flipTransition.getChildren().addAll(scaleTo0, rotate, scaleBackTo1, rotateTransition);
        flipTransition.play();
    }

    private void stand() {
        revealFlipped();
        checkDealerOverMax();
    }

    private void checkDealerOverMax() {
        if (blackJack.getDealerScore() < BLACKJACK)
            takeCard(false, blackJack.getDeck().get(getDecksFirst()), blackJack.getDealerSlots().get(getDealerFreeSlot()), () -> {
                calculateScore();
                checkDealerOverMax();
            });
        else buttonsActive(true);
    }

    private void checkPlayerOverMax() {
        if (blackJack.getPlayerScore() < BLACKJACK)
            takeCard(false, blackJack.getDeck().get(getDecksFirst()), blackJack.getPlayerSlots().get(getPlayerFreeSlot()), () -> {
                calculateScore();
                checkPlayerOverMax();
            });
        else buttonsActive(true);
    }
}
