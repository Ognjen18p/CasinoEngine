package com.controller.game.table_game;

import com.application.configuration.CasinoConfiguration;
import com.basis.game.table_game.Chip;
import com.basis.game.table_game.ChipShop;
import com.basis.game.table_game.TableGame;
import com.controller.game.GameController;
import javafx.animation.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class TableGameController<G extends TableGame> extends GameController<G> {

    protected ChipShop chipShop;

    public ChipShop getChipShop() {
        return chipShop;
    }

    public void setChipShop(ChipShop chipShop) {
        this.chipShop = chipShop;
    }
    public TableGameController() {
        super();
    }

    protected abstract void onChipSelected(Chip chip);

    protected abstract void purchasedChipsListener();


    private ArrayList<Chip> winChips() {
        double toPayout = game.getWin() - game.getBet();
        double reminder = toPayout % CasinoConfiguration.BETTING_VALUES[0];
        toPayout -= reminder;

        Map<Integer, Integer> payoutValues = new HashMap<>();
        for (int value = CasinoConfiguration.BETTING_VALUES.length - 1; value >= 0; value--) {
            int chipValue = CasinoConfiguration.BETTING_VALUES[value];
            if (chipValue <= toPayout) {
                int divider = (int) (toPayout / chipValue);
                toPayout -= divider * chipValue;
                payoutValues.put(chipValue, divider);
            }
        }

        ArrayList<Chip> earnedChips = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : payoutValues.entrySet()) {
            int amountOfChips = entry.getValue();
            for (int nChip = 0; nChip < amountOfChips; nChip++) {
                for (Chip shopChip : chipShop.getAvailableChips()) {
                    if (shopChip.getValue() == entry.getKey()) {
                        Chip newChip = new Chip(entry.getKey(), shopChip.getButton().getLayoutX(), -100, (int)
                                game.getBettingChips().getFirst().getSize(), true);
                        earnedChips.add(newChip);
                        break;
                    }
                }
            }
        }
        return earnedChips;
    }

    protected final Transition takeWinningChips() {

        ArrayList<Chip> earnedChips = winChips();

        SequentialTransition sequentialTransition = new SequentialTransition();
        for (Chip earnedChip : earnedChips) {
            for (Chip destinationChip : game.getOwningChips()) {
                if (destinationChip.getValue() == earnedChip.getValue()) {
                    Timeline transition = new Timeline(
                            new KeyFrame(Duration.millis(300),
                                    new KeyValue(earnedChip.getButton().layoutXProperty(), destinationChip.getImage().getLayoutX()),
                                    new KeyValue(earnedChip.getButton().layoutYProperty(), destinationChip.getImage().getLayoutY())
                            ));
                    game.getMainPane().getChildren().add(earnedChip.getButton());
                    transition.setOnFinished(event -> {
                        game.getOwningChips().add(earnedChip);
                    });
                    sequentialTransition.getChildren().add(transition);
                    break;
                }
            }
        }
        return sequentialTransition;
    }

    protected Transition returnBettingChips() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ArrayList<Chip> resetChips = new ArrayList<>();
        for (Chip bettingChip : game.getBettingChips()) {
            if (bettingChip.getButton() != null) {
                for (Chip ownedChip : game.getOwningChips()) {
                    if (ownedChip.getValue() == bettingChip.getValue()) {
                        Timeline moveChipTransition = new Timeline(
                                new KeyFrame(Duration.millis(400),
                                        new KeyValue(bettingChip.getButton().layoutXProperty(), ownedChip.getImage().getLayoutX()),
                                        new KeyValue(bettingChip.getButton().layoutYProperty(), ownedChip.getImage().getLayoutY())
                                ));
                        sequentialTransition.getChildren().add(moveChipTransition);
                        resetChips.add(bettingChip);
                        break;
                    }
                }
            }
        }
        sequentialTransition.setOnFinished(event -> {
            for (Chip rChip : resetChips) {
                rChip.setSelected(false);
                game.getBettingChips().remove(rChip);
                game.getOwningChips().add(rChip);
            }
        });
        return sequentialTransition;
    }


}
