package com.controller.game.machine_game;

import com.application.configuration.GameSettings;
import com.application.utilities.Vector2;
import com.basis.game.Game;
import com.basis.game.machine_game.Reel;
import com.basis.game.machine_game.Slot;
import com.basis.game.machine_game.symbols.SymbolInfo;
import com.basis.transaction.Transaction;
import com.controller.Controller;
import com.controller.game.GameController;
import com.controller.page.MenuPageController;
import com.database.person.PlayerDAO;
import com.stylization.game.SlotStylization;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import com.application.GameManager;

public class SlotController extends GameController {
    private Slot slot;

    public SlotController() {
        super();
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        handleExit(slot);
        handleDeposit(slot);
        handleSpin();
        handleBetting();
    }

    @Override
    protected void initializeScene() {
        slot = new Slot(new Vector2(5, 3), 10, 1000);

        new SlotStylization(slot);

        scene = new Scene(slot.getMainPane(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("SLOT");
    }

    private void handleBetting() {
        slot.getAddBetButton().setOnAction(event -> {
            slot.setBet(Math.min(slot.getMaximumBet(), slot.getBet() + 10));
            slot.getSpinButton().setDisable(slot.getBet() < slot.getMinimumBet());
        });
        slot.getRemoveBetButton().setOnAction(event -> {
            slot.setBet(Math.max(slot.getMinimumBet(), slot.getBet() - 10));
        });
    }

    private void handleSpin() {
        slot.getSpinButton().setOnAction(event -> {
            if (slot.getBet() > slot.getMinimumBet()) {
                playerDAO.updateBalance(-slot.getBet());
                transactionDAO.createTransaction(
                        new Transaction(0, GameManager.getInstance().getCurrentPlayer().getId(),
                        slot.getBet(), Transaction.Status.APPROVED, Transaction.Type.BET)
                );
                slot.setBalance(GameManager.getInstance().getCurrentPlayer().getBalance());
                for (Reel reel : slot.getReels())
                    reel.replacement();
                slot.getSpinButton().setDisable(true);
                spin();
            }

        });
    }

    private void spin() {
        AnimationTimer spinTimer = new AnimationTimer() {
            long previousTime = 0;
            long startTime = 0;
            double velocity = 3500;
            final double spinDuration = 1;
            //            final double deceleration = velocity / spinDuration;
            boolean[] stoppedReels = new boolean[slot.getReels().size()];

            @Override
            public void handle(long currentTime) {
                if (startTime == 0) {
                    startTime = currentTime;
                    previousTime = currentTime;
                    return;
                }
                double deltaTime = (currentTime - previousTime) / 1_000_000_000.0;
                double elapsedTime = (currentTime - startTime) / 1_000_000_000.0;
                previousTime = currentTime;

                for (int nReel = 0; nReel < slot.getReels().size(); nReel++) {
                    if (stoppedReels[nReel]) continue;
                    Reel reel = slot.getReels().get(nReel);
                    boolean timeStop = elapsedTime > spinDuration + nReel + 1;
                    stoppedReels[nReel] = reel.reelSpin(deltaTime, velocity, timeStop);
                }

                boolean allStopped = true;
                for (boolean stopped : stoppedReels)
                    allStopped = allStopped && stopped;
                if (allStopped) {
                    checkWinner();
                    slot.getSpinButton().setDisable(GameManager.getInstance().getCurrentPlayer().getBalance() < slot.getMinimumBet());
                    stop();
                }
            }
        };
        spinTimer.start();
    }

    public void checkWinner() {
        int numberOfRows = (int) slot.getSlotGridSize().getY();
        int winAmount = 0;

        for (int nSymbol = 0; nSymbol < numberOfRows; nSymbol++) {
            SymbolInfo startingReel = slot.getReels().getFirst().getWinningSymbols().get(nSymbol).getSymbolInfo();
            int connectedCounter = 1;
            for (int nReel = 1; nReel < slot.getReels().size(); nReel++) {
                SymbolInfo currentSymbol = slot.getReels().get(nReel).getWinningSymbols().get(nSymbol).getSymbolInfo();
                if (currentSymbol.getId().equals(startingReel.getId()))
                    connectedCounter++;
                else break;
            }
            if (connectedCounter >= 3)
                winAmount += startingReel.getPayouts()[connectedCounter - 1];
        }

        if (winAmount > 0) {
            playerDAO.updateBalance(winAmount);
            slot.setBalance(GameManager.getInstance().getCurrentPlayer().getBalance());
        }
    }

}

