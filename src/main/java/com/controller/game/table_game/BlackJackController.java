package com.controller.game.table_game;

import com.application.configuration.CasinoConfiguration;
import com.application.configuration.GameSettings;
import com.application.utilities.Vector2;
import com.basis.game.table_game.ChipShop;
import com.basis.game.table_game.blackjack.BlackJack;
import com.basis.game.table_game.blackjack.PlayingCard;
import com.basis.game.table_game.Chip;
import com.stylization.game.BlackJackStylization;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import com.application.GameManager;

import java.util.ArrayList;


public class BlackJackController extends TableGameController<BlackJack> {

    public BlackJackController() {
        super();
        initializeScene();
        setupEventHandlers();

    }

    @Override
    protected void setupEventHandlers() {
        handleExit(game);
        handleDeposit(game);
        handleDeal();
        handleHit();
        handleStand();
        purchasedChipsListener();
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("BLACKJACK");
    }

    @Override
    public void onReset() {
        game.setBalance(GameManager.getInstance().getCurrentPlayer().getBalance());
    }

    @Override
    protected void initializeScene() {
        game = new BlackJack();

        chipShop = new ChipShop(game, playerDAO, new Vector2(600, 80), new Vector2(100, 300));

        scene = new Scene(game.getMainPane(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());

        new BlackJackStylization(game);
    }

    @Override
    protected void purchasedChipsListener() {
        game.getOwningChips().addListener((ListChangeListener<Chip>) chips -> {
            while (chips.next()) {
                if (chips.wasAdded()) {
                    for (Chip addedChip : chips.getAddedSubList()) {
                        if (addedChip.getButton() != null)
                            addedChip.getButton().setOnMouseClicked(actionEvent -> {
                                if (actionEvent.getButton() == MouseButton.SECONDARY) {
                                    chipShop.sellChipTransition(addedChip);
                                } else {
                                    onChipSelected(addedChip);
                                }
                            });
                    }
                }
            }
        });
        game.addOwningChips();
    }

    @Override
    protected void onChipSelected(Chip chip) {
        Timeline moveChipTransition = new Timeline();
        chip.getButton().toFront();
        if (chip.isSelected()) {
            for (Chip owningChip : game.getOwningChips()) {
                if (owningChip.getValue() == chip.getValue()) {
                    moveChipTransition.getKeyFrames().setAll(
                            new KeyFrame(Duration.millis(300),
                                    new KeyValue(chip.getButton().layoutXProperty(), owningChip.getImage().getLayoutX()),
                                    new KeyValue(chip.getButton().layoutYProperty(), owningChip.getImage().getLayoutY())
                            )
                    );
                    game.setBet(Math.max(0, game.getBet() - chip.getValue()));
                    if (game.getBet() < CasinoConfiguration.MIN_BET) {
                        game.getPlaceYourBetsLabel().setVisible(true);
                        game.getDealButton().setVisible(false);
                    }
                    break;
                }
            }
            chip.getButton().setDisable(true);
            game.getBettingChips().remove(chip);
            game.getOwningChips().add(chip);
        } else {
            moveChipTransition.getKeyFrames().setAll(
                    new KeyFrame(Duration.millis(300),
                            new KeyValue(chip.getButton().layoutXProperty(), game.getBettingChips().getFirst().getImage().getLayoutX()),
                            new KeyValue(chip.getButton().layoutYProperty(), game.getBettingChips().getFirst().getImage().getLayoutY())
                    )
            );
            chip.getButton().setDisable(true);
            game.setBet(Math.min(game.getBet() + chip.getValue(), CasinoConfiguration.MAX_BET));
            game.getOwningChips().remove(chip);
            game.getBettingChips().add(chip);
            if (game.getBet() >= CasinoConfiguration.MIN_BET && !game.getDealButton().isVisible()) {
                game.getPlaceYourBetsLabel().setVisible(false);
                game.getDealButton().setVisible(true);
            }
        }
        moveChipTransition.setOnFinished(event -> {
            chip.setSelected(!chip.isSelected());
            chip.getButton().setDisable(false);
        });
        moveChipTransition.play();
    }

    private void handleDeal() {
        game.getDealButton().setOnAction(actionEvent -> {
            game.getDealButton().setVisible(false);
            deal();
        });
    }

    private void handleHit() {
        game.getHitButton().setOnAction(actionEvent -> {
            game.buttonsHitStandActivation(false);
            hit();
        });
    }

    private void handleStand() {
        game.getStandButton().setOnAction(actionEvent -> {
            game.buttonsHitStandActivation(false);
            stand();
        });
    }

    private Transition pullCard(boolean flipped, boolean isPlayer, PlayingCard pulledCard, PlayingCard destinationCard) {
        destinationCard.setRank(pulledCard.getRank());
        destinationCard.setSuit(pulledCard.getSuit());

        if (flipped) {
            Image savedImage = pulledCard.getImage().getImage();
            pulledCard.getImage().setImage(game.getDealersFlippedCard().getImage().getImage());
            game.getDealersFlippedCard().getImage().setImage(savedImage);
            game.getDealerCards().add(pulledCard);
            game.addScoreDealer(pulledCard);
        } else if (isPlayer) {
            game.getPlayerCards().add(pulledCard);
            game.addScorePlayer(pulledCard);
        } else {
            game.getDealerCards().add(pulledCard);
            game.addScoreDealer(pulledCard);
        }

        Timeline moveCardTransition = new Timeline(
                new KeyFrame(Duration.millis(400),
                        new KeyValue(pulledCard.getImage().layoutYProperty(), destinationCard.getImage().getLayoutY()),
                        new KeyValue(pulledCard.getImage().layoutXProperty(), destinationCard.getImage().getLayoutX()),
                        new KeyValue(pulledCard.getImage().rotateProperty(), 0)
                )
        );
        pulledCard.getImage().toFront();

        return new ParallelTransition(moveCardTransition);
    }

    private void deal() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                pullCard(false, true, game.getDeck().drawCard(), game.getPlayerSlots().get(game.getAndIncrementPlayerSlot())),
                pullCard(true, false, game.getDeck().drawCard(), game.getDealerSlots().get(game.getAndIncrementDealerSlot())),
                pullCard(false, true, game.getDeck().drawCard(), game.getPlayerSlots().get(game.getAndIncrementPlayerSlot())),
                pullCard(false, false, game.getDeck().drawCard(), game.getDealerSlots().get(game.getAndIncrementDealerSlot())));
        sequentialTransition.setOnFinished(event -> {
            if (game.getPlayerScore() == BlackJack.BLACKJACK) {
                game.setBlackJackWinState(BlackJack.BlackJackWinState.PLAYER_BLACKJACK);
                Transition revealFlipped = revealFlippedTransition();
                revealFlipped.setOnFinished(actionEvent -> {
                    if (game.getDealerScore() == BlackJack.BLACKJACK)
                        game.setBlackJackWinState(BlackJack.BlackJackWinState.PUSH);
                    checkWinner();
                });
                revealFlipped.play();
            } else if (game.getDealerScore() == BlackJack.BLACKJACK) {
                game.setBlackJackWinState(BlackJack.BlackJackWinState.DEALER_BLACKJACK);
                Transition revealFlipped = revealFlippedTransition();
                revealFlipped.setOnFinished(actionEvent -> checkWinner());
                revealFlipped.play();
            } else
                game.buttonsHitStandActivation(true);
        });
        sequentialTransition.play();
    }

    private void hit() {
        game.buttonsHitStandActivation(false);
        if (game.getPlayerScore() <= BlackJack.BLACKJACK) {
            Transition pullCard = pullCard(false, true, game.getDeck().drawCard(), game.getPlayerSlots().get(game.getAndIncrementPlayerSlot()));
            pullCard.setOnFinished(event -> {
                if (game.getPlayerScore() > BlackJack.BLACKJACK) {
                    Transition revealFlipped = revealFlippedTransition();
                    revealFlipped.setOnFinished(actionEvent -> {
                        game.setBlackJackWinState(BlackJack.BlackJackWinState.DEALER_WINS);
                        checkWinner();
                    });
                    revealFlipped.play();
                } else game.buttonsHitStandActivation(true);
            });
            pullCard.play();
        }
    }

    private Transition revealFlippedTransition() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ParallelTransition parallelTransition = new ParallelTransition();
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(500));

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(500), game.getDealerCards().getFirst().getImage());
        scaleDown.setToX(0);
        scaleDown.setOnFinished(event -> {
            game.getDealerCards().getFirst().getImage().setImage(game.getDealersFlippedCard().getImage().getImage());
        });

        RotateTransition flip = new RotateTransition(Duration.millis(0), game.getDealerCards().getFirst().getImage());
        flip.setAxis(Rotate.Y_AXIS);
        flip.setByAngle(180);
        flip.setAutoReverse(true);

        ScaleTransition scaleBack = new ScaleTransition(Duration.millis(500), game.getDealerCards().getFirst().getImage());
        scaleBack.setToX(1);

        RotateTransition rotate = new RotateTransition(Duration.millis(500), game.getDealerCards().getFirst().getImage());
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(180);
        rotate.setAutoReverse(true);

        parallelTransition.getChildren().addAll(scaleDown, rotate, flip, scaleBack);
        sequentialTransition.getChildren().addAll(pauseTransition, parallelTransition);
        return sequentialTransition;
    }

    private void dealerPullCard() {
        if (game.getDealerScore() <= 16) {
            Transition pullCard = pullCard(false, false, game.getDeck().drawCard(), game.getDealerSlots().get(game.getAndIncrementDealerSlot()));
            pullCard.setOnFinished(event -> {
                dealerPullCard();
            });
            pullCard.play();
        } else if (game.getDealerScore() == game.getPlayerScore()) {
            game.setBlackJackWinState(BlackJack.BlackJackWinState.PUSH);
            checkWinner();
        } else if (game.getDealerScore() < game.getPlayerScore() || game.getDealerScore() > BlackJack.BLACKJACK) {
            game.setBlackJackWinState(BlackJack.BlackJackWinState.PLAYER_WINS);
            checkWinner();
        } else {
            game.setBlackJackWinState(BlackJack.BlackJackWinState.DEALER_WINS);
            checkWinner();
        }
    }

    private void stand() {
        game.buttonsHitStandActivation(false);
        Transition revealFlipped = revealFlippedTransition();
        revealFlipped.setOnFinished(event -> dealerPullCard());
        revealFlipped.play();
    }

    private void checkWinner() {
        if (game.getBlackJackWinState() != BlackJack.BlackJackWinState.IN_PROGRESS) {
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setDuration(Duration.millis(600));
            pauseTransition.setOnFinished(event -> {
                System.out.println("Winner: " + game.getBlackJackWinState().toString());
                SequentialTransition transition = new SequentialTransition();
                switch (game.getBlackJackWinState()) {
                    case PUSH:
                        transition.getChildren().add(returnBettingChips());
                        System.out.println("PUSH");
                        break;
                    case PLAYER_WINS:
                        game.setWin(game.getBet() * 2);
                        transition.getChildren().addAll(takeWinningChips(), returnBettingChips());
                        break;
                    case PLAYER_BLACKJACK:
                        game.setWin((int) (game.getBet() * 2.5));
                        transition.getChildren().addAll(takeWinningChips(), returnBettingChips());
                        System.out.println("PLAYER BLACKJACK");
                        break;
                    case DEALER_WINS:
                        transition.getChildren().add(clearBet());
                        System.out.println("DEALER win");
                        break;
                    case DEALER_BLACKJACK:
                        transition.getChildren().addAll(clearBet());
                        System.out.println("DEALER BLACKJACK");
                        break;
                    default:
                        return;
                }
                transition.getChildren().add(removeCards());
                transition.play();
            });
            pauseTransition.play();
        }
    }

    private Transition clearBet() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ArrayList<Chip> removeChips = new ArrayList<>();
        for (Chip nChip : game.getBettingChips()) {
            if (nChip.getButton() != null) {
                TranslateTransition moveChipTransition = new TranslateTransition(Duration.millis(400), nChip.getButton());
                moveChipTransition.setByY((-100) - nChip.getButton().getLayoutY());
                sequentialTransition.getChildren().add(moveChipTransition);
                removeChips.add(nChip);
            }
        }
        sequentialTransition.setOnFinished(event -> {
            for (Chip rChip : removeChips) {
                GameManager.getInstance().getCurrentPlayer().removeFromOwningChips(rChip.getValue());
                if (!playerDAO.saleChip())
                    System.out.println(playerDAO.getErrorMessage());
                game.getBettingChips().remove(rChip);
                game.getMainPane().getChildren().remove(rChip.getButton());
                game.setWin(0);
            }
        });
        return sequentialTransition;
    }

    private Transition removeCards() {
        ParallelTransition parallelTransition = new ParallelTransition();
        for (PlayingCard playerCard : game.getPlayerCards()) {
            Timeline moveCardAnimation = new Timeline(
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(playerCard.getImage().layoutYProperty(), -100))
            );
            parallelTransition.getChildren().add(moveCardAnimation);
        }
        for (PlayingCard dealerCard : game.getDealerCards()) {
            Timeline moveCardAnimation = new Timeline(
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(dealerCard.getImage().layoutYProperty(), -100))
            );
            parallelTransition.getChildren().add(moveCardAnimation);
        }
        parallelTransition.setOnFinished(event -> {
            game.getDeck().getCards().forEach(pulledCard -> {
                game.getMainPane().getChildren().remove(pulledCard.getImage());
            });
            game.getPlayerCards().clear();
            game.getDealerCards().clear();
            game.reset();
        });
        return parallelTransition;
    }

}