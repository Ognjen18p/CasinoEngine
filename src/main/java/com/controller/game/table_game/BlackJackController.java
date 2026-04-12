package com.controller.game.table_game;

import com.basis.game.essentials.GameSettings;
import com.basis.game.essentials.Vector2;
import com.basis.game.table_game.TableGame;
import com.basis.game.table_game.blackjack.BlackJack;
import com.basis.game.table_game.blackjack.Card;
import com.basis.game.table_game.blackjack.Chip;
import com.basis.game.table_game.ChipShop;
import com.controller.game.TableGameController;
import com.stylization.game.BlackJackStylization;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import com.manager.GameManager;

import java.util.ArrayList;

public class BlackJackController extends TableGameController {
    private BlackJack blackJack;
    private ChipShop chipShop;

    public BlackJackController() {
    }

    @Override
    protected void setupEventHandlers() {
        handleDeal();
        handleHit();
        handleStand();
        handleChipShop();
        purchasedChip(blackJack);
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("BLACKJACK");
    }

    @Override
    protected void initializeScene() {
        blackJack = new BlackJack();

        chipShop = new ChipShop(blackJack);

        scene = new Scene(blackJack.getMainPane(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());

        BlackJackStylization blackJackStylization = new BlackJackStylization(blackJack);
    }

    @Override
    protected void purchasedChip(TableGame game) {
        game.getOwningChips().addListener((ListChangeListener<Chip>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Chip chip : change.getAddedSubList()) {
                        if (chip.getButton() != null)
                            chip.getButton().setOnAction(actionEvent -> selectingChip(chip));
                    }
                }
            }
        });
    }

    @Override
    protected void selectingChip(Chip chip) {
        TranslateTransition moveChip = new TranslateTransition(Duration.millis(1200), chip.getButton());
        chip.getButton().toFront();

        if (chip.isSelected()) {
            for (Chip nChip : blackJack.getOwningChips()) {
                if (nChip.getValue() == chip.getValue()) {
                    blackJack.setBet(blackJack.getBet() - chip.getValue());
                    moveChip.setToY(nChip.getImage().getY() - chip.getButton().getLayoutY());
                    moveChip.setToX(nChip.getImage().getX() - chip.getButton().getLayoutX());
                    if (blackJack.getBet() <= 0) {
                        blackJack.setBet(0);
                        blackJack.getPlaceYourBetsLabel().setVisible(true);
                        blackJack.getDealButton().setVisible(false);
                    }
                    break;
                }
            }
            chip.getButton().setDisable(true);
            blackJack.getBettingChips().remove(chip);
            blackJack.getOwningChips().add(chip);
        } else {
            blackJack.getPlaceYourBetsLabel().setVisible(false);
            blackJack.getDealButton().setVisible(true);
            blackJack.setBet(blackJack.getBet() + chip.getValue());
            moveChip.setToY(blackJack.getBettingChips().getFirst().getImage().getY() - chip.getButton().getLayoutY());
            moveChip.setToX(blackJack.getBettingChips().getFirst().getImage().getX() - chip.getButton().getLayoutX());
            chip.getButton().setDisable(true);
            blackJack.getOwningChips().remove(chip);
            blackJack.getBettingChips().add(chip);
        }
        moveChip.setOnFinished(event -> {
            chip.setSelected(!chip.isSelected());
            chip.getButton().setDisable(false);
        });
        moveChip.play();
    }


    private void handleDeal() {
        blackJack.getDealButton().setOnAction(actionEvent -> {
            blackJack.getDealButton().setVisible(false);
            deal();
        });
    }

    private void handleHit() {
        blackJack.getHitButton().setOnAction(actionEvent -> {
            blackJack.buttonsHitStandActivation(false);
            hit();
        });
    }

    private void handleStand() {
        blackJack.getStandButton().setOnAction(actionEvent -> {
            blackJack.buttonsHitStandActivation(false);
            stand();
        });
    }

    private void handleChipShop() {
        blackJack.getChipShopButton().setOnAction(actionEvent ->
                chipShop.getMainPane().setVisible(!chipShop.getMainPane().isVisible()));
    }

    private enum Puller {PLAYER, DEALER}

    private Transition pullCard(boolean flipped, Puller puller, Card card, Card destination) {
        destination.setRank(card.getRank());
        destination.setSuit(card.getSuit());

        if (flipped) {
            Image savedImage = card.getImage().getImage();
            card.getImage().setImage(blackJack.getDealersFlippedCard().getImage().getImage());
            blackJack.getDealersFlippedCard().getImage().setImage(savedImage);
            blackJack.getDealerCards().add(card);
            blackJack.addScoreDealer(card);
        } else if (puller == Puller.PLAYER) {
            blackJack.getPlayerCards().add(card);
            blackJack.addScorePlayer(card);
        } else {
            blackJack.getDealerCards().add(card);
            blackJack.addScoreDealer(card);
        }

        TranslateTransition moveCard = new TranslateTransition(Duration.millis(1200), card.getImage());
        card.getImage().toFront();
        card.getImage().setTranslateX(0);
        card.getImage().setTranslateY(0);
        moveCard.setToX(destination.getImage().getX() - card.getImage().getX());
        moveCard.setToY(destination.getImage().getY() - card.getImage().getY());

        RotateTransition rotateCard = new RotateTransition(Duration.millis(600), card.getImage());
        rotateCard.setByAngle(-20);

        return new ParallelTransition(moveCard, rotateCard);
    }

    private void deal() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                pullCard(false, Puller.PLAYER, blackJack.getDeck().drawCard(), blackJack.getPlayerSlots().get(blackJack.getAndIncrementPlayerSlot())),
                pullCard(true, Puller.DEALER, blackJack.getDeck().drawCard(), blackJack.getDealerSlots().get( blackJack.getAndIncrementDealerSlot())),
                pullCard(false, Puller.PLAYER, blackJack.getDeck().drawCard(), blackJack.getPlayerSlots().get(blackJack.getAndIncrementPlayerSlot())),
                pullCard(false, Puller.DEALER, blackJack.getDeck().drawCard(), blackJack.getDealerSlots().get(blackJack.getAndIncrementDealerSlot())));
        sequentialTransition.setOnFinished(event -> {
            if (blackJack.getPlayerScore() == blackJack.getBLACKJACK()) {
                blackJack.setBlackJackWinState(BlackJack.BlackJackWinState.PLAYER_BLACKJACK);
                Transition revealFlipped = revealFlipped();
                revealFlipped.setOnFinished(actionEvent -> {
                    if (blackJack.getDealerScore() == blackJack.getBLACKJACK())
                        blackJack.setBlackJackWinState(BlackJack.BlackJackWinState.PUSH);
                    checkWinner();
                });
                revealFlipped.play();
            } else if (blackJack.getDealerScore() == blackJack.getBLACKJACK()) {
                blackJack.setBlackJackWinState(BlackJack.BlackJackWinState.DEALER_BLACKJACK);
                Transition revealFlipped = revealFlipped();
                revealFlipped.setOnFinished(actionEvent -> checkWinner());
                revealFlipped.play();
            } else
                blackJack.buttonsHitStandActivation(true);
        });
        sequentialTransition.play();
    }

    private void hit() {
        if (blackJack.getPlayerScore() < blackJack.getBLACKJACK()) {
            Transition pullCard = pullCard(false, Puller.PLAYER, blackJack.getDeck().drawCard(), blackJack.getPlayerSlots().get(blackJack.getAndIncrementPlayerSlot()));
            pullCard.setOnFinished(event -> {
                if (blackJack.getPlayerScore() > blackJack.getBLACKJACK()) {
                    Transition revealFlipped = revealFlipped();
                    revealFlipped.setOnFinished(actionEvent -> {
                        blackJack.setBlackJackWinState(BlackJack.BlackJackWinState.DEALER_WINS);
                        checkWinner();
                    });
                    revealFlipped.play();
                }
                blackJack.buttonsHitStandActivation(true);
            });
            pullCard.play();
        }
    }

    private Transition revealFlipped() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ParallelTransition parallelTransition = new ParallelTransition();
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(1200));

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(1200), blackJack.getDealerCards().getFirst().getImage());
        scaleDown.setToX(0);
        scaleDown.setOnFinished(event -> {
            blackJack.getDealerCards().getFirst().getImage().setImage(blackJack.getDealersFlippedCard().getImage().getImage());
        });

        RotateTransition flip = new RotateTransition(Duration.millis(0), blackJack.getDealerCards().getFirst().getImage());
        flip.setAxis(Rotate.Y_AXIS);
        flip.setByAngle(180);
        flip.setAutoReverse(true);

        ScaleTransition scaleBack = new ScaleTransition(Duration.millis(1200), blackJack.getDealerCards().getFirst().getImage());
        scaleBack.setToX(1);

        RotateTransition rotate = new RotateTransition(Duration.millis(1200), blackJack.getDealerCards().getFirst().getImage());
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(180);
        rotate.setAutoReverse(true);

        parallelTransition.getChildren().addAll(scaleDown, rotate, flip, scaleBack);
        sequentialTransition.getChildren().addAll(pauseTransition, parallelTransition);
        return sequentialTransition;
    }

    private void dealerPullCard() {
        if (blackJack.getDealerScore() <= 16) {
            Transition pullCard = pullCard(false, Puller.DEALER, blackJack.getDeck().drawCard(), blackJack.getDealerSlots().get(blackJack.getAndIncrementDealerSlot()));
            pullCard.setOnFinished(event -> {
                dealerPullCard();
            });
            pullCard.play();
        } else if (blackJack.getDealerScore() == blackJack.getPlayerScore()) {
            blackJack.setBlackJackWinState(BlackJack.BlackJackWinState.PUSH);
            checkWinner();
        } else if (blackJack.getDealerScore() < blackJack.getPlayerScore() || blackJack.getDealerScore() > blackJack.getBLACKJACK()) {
            blackJack.setBlackJackWinState(BlackJack.BlackJackWinState.PLAYER_WINS);
            checkWinner();
        } else {
            blackJack.setBlackJackWinState(BlackJack.BlackJackWinState.DEALER_WINS);
            checkWinner();
        }
    }

    private void stand() {
        Transition revealFlipped = revealFlipped();
        revealFlipped.setOnFinished(event -> dealerPullCard());
        revealFlipped.play();
    }

    private void checkWinner() {
        if (blackJack.getBlackJackWinState() != BlackJack.BlackJackWinState.IN_PROGRESS) {
            blackJack.buttonsHitStandActivation(false);
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setDuration(Duration.millis(1100));
            pauseTransition.setOnFinished(event -> {
                System.out.println("Winner: " + blackJack.getBlackJackWinState().toString());
                SequentialTransition transition = new SequentialTransition();
                switch (blackJack.getBlackJackWinState()) {
                    case PUSH:
                        transition.getChildren().addAll(resetBet(), removeCards());
                        System.out.println("PUSH");
                        break;
                    case PLAYER_WINS:
                        transition.getChildren().addAll(takeBet(), removeCards());
                        break;
                    case PLAYER_BLACKJACK:
                        transition.getChildren().addAll(takeBet(), removeCards());
                        System.out.println("PLAYER BLACKJACK");
                        break;
                    case DEALER_WINS:
                        transition.getChildren().addAll(clearBet(), removeCards());
                        System.out.println("DEALER win");
                        break;
                    case DEALER_BLACKJACK:
                        transition.getChildren().addAll(clearBet(), removeCards());
                        System.out.println("DEALER BLACKJACK");
                        break;
                    default:
                        return;
                }
                transition.play();
            });
            pauseTransition.play();
        }
    }

    private Transition resetBet() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ArrayList<Chip> resetChips = new ArrayList<>();
        for (Chip bChip : blackJack.getBettingChips()) {
            if (bChip.getButton() != null && bChip.isSelected()) {
                for (Chip oChip : blackJack.getOwningChips()) {
                    if (oChip.getValue() == bChip.getValue()) {
                        TranslateTransition moveChip = new TranslateTransition(Duration.millis(1100), bChip.getButton());
                        bChip.getButton().setTranslateX(0);
                        bChip.getButton().setTranslateY(0);
                        moveChip.setByY(oChip.getImage().getY() - bChip.getButton().getLayoutY());
                        moveChip.setByX(oChip.getImage().getX() - bChip.getButton().getLayoutX());
                        sequentialTransition.getChildren().add(moveChip);
                        resetChips.add(bChip);
                    }
                }
            }
        }
        sequentialTransition.setOnFinished(event -> {
            for (Chip aChip : resetChips) {
                blackJack.getBettingChips().remove(aChip);
                blackJack.getOwningChips().add(aChip);
            }
            blackJack.setBalance(blackJack.getBalance() + blackJack.getBet());
            blackJack.setWin(0);
        });
        return sequentialTransition;
    }

    private Transition takeBet() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ArrayList<Chip> addChips = new ArrayList<>();
        for (Chip bChip : blackJack.getBettingChips()) {
            if (bChip.isSelected()) {
                for (Chip oChip : blackJack.getOwningChips()) {
                    if (oChip.getValue() == bChip.getValue()) {
                        TranslateTransition moveChip = new TranslateTransition(Duration.millis(1100), bChip.getButton());
                        bChip.getButton().setTranslateX(0);
                        bChip.getButton().setTranslateY(0);
                        moveChip.setByY(oChip.getImage().getY() - bChip.getButton().getLayoutY());
                        moveChip.setByX(oChip.getImage().getX() - bChip.getButton().getLayoutX());
                        sequentialTransition.getChildren().add(moveChip);
                        addChips.add(bChip);
                    }
                }
            }
        }
        sequentialTransition.setOnFinished(event -> {
            for (Chip aChip : addChips) {
                int addBalance = (blackJack.getBet() * 2);
                if (blackJack.getBlackJackWinState() == BlackJack.BlackJackWinState.PLAYER_BLACKJACK)
                    addBalance = ((int) (blackJack.getBet() * 2.5));

                blackJack.setWin(addBalance);
                blackJack.setLastWin(addBalance);
                blackJack.setTotalWin(blackJack.getTotalWin() + addBalance);
                blackJack.setBalance(blackJack.getBalance() + addBalance);

                aChip.setSelected(false);
                blackJack.getBettingChips().remove(aChip);
                blackJack.getOwningChips().add(aChip);
            }
        });
        return sequentialTransition;
    }

    private Transition clearBet() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ArrayList<Chip> removeChips = new ArrayList<>();
        for (Chip nChip : blackJack.getBettingChips()) {
            if (nChip.getButton() != null) {
                TranslateTransition moveChip = new TranslateTransition(Duration.millis(1100), nChip.getButton());
                moveChip.setByY((-100) - nChip.getButton().getLayoutY());
                sequentialTransition.getChildren().add(moveChip);
                removeChips.add(nChip);
            }
        }
        sequentialTransition.setOnFinished(event -> {
            for (Chip rChip : removeChips) {
                blackJack.getBettingChips().remove(rChip);
                blackJack.getMainPane().getChildren().remove(rChip.getButton());
                blackJack.setWin(0);
            }
        });
        return sequentialTransition;
    }

    private Transition removeCards() {
        SequentialTransition parallelTransition = new SequentialTransition();
        for (Card nCard : blackJack.getPlayerCards()) {
            TranslateTransition moveCard = new TranslateTransition(Duration.millis(1100), nCard.getImage());
            moveCard.setByY(-400 - nCard.getImage().getY());
            parallelTransition.getChildren().add(moveCard);
        }
        for (Card nCard : blackJack.getDealerCards()) {
            TranslateTransition moveCard = new TranslateTransition(Duration.millis(1100), nCard.getImage());
            moveCard.setByY(-400 - nCard.getImage().getY());
            parallelTransition.getChildren().add(moveCard);
        }
        parallelTransition.setOnFinished(event -> {
            blackJack.getDeck().getCards().forEach(card -> {
                blackJack.getMainPane().getChildren().remove(card.getImage());
            });
            blackJack.getPlayerCards().clear();
            blackJack.getDealerCards().clear();
            blackJack.reset();
        });
        return parallelTransition;
    }

}
