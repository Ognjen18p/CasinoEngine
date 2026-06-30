package com.controller.game.machine_game;

import com.application.configuration.CasinoConfiguration;
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

import java.util.ArrayList;
import java.util.List;

public class SlotController extends GameController<Slot> {

    public SlotController() {
        super();
        initializeScene();
        setupEventHandlers();

//        int spins = 100_000;
//        game.setBet(1);
//        double win = 0;
//        for (int i = 0; i < spins; i++) {
//            for (Reel reel : game.getReels())
//                reel.replacement();
//            win += checkWinner();
//        }
//        System.out.println(" end: " + win / spins);
    }

    @Override
    protected void setupEventHandlers() {
        handleExit(game);
        handleDeposit(game);
        handleSpin();
        handleBetting();
    }

    @Override
    protected void initializeScene() {
        game = new Slot(new Vector2(5, 3));

        new SlotStylization(game);

        scene = new Scene(game.getMainPane(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Slot");
    }

    @Override
    public void onReset() {
        game.setBalance(GameManager.getInstance().getCurrentPlayer().getBalance());
    }

    private void handleBetting() {
        game.getAddBetButton().setOnAction(event -> {
            game.setBet(Math.min(CasinoConfiguration.MAX_BET, game.getBet() + 10));
            game.getSpinButton().setDisable(game.getBet() < CasinoConfiguration.MIN_BET);
        });
        game.getRemoveBetButton().setOnAction(event -> {
            game.setBet(Math.max(CasinoConfiguration.MIN_BET, game.getBet() - 10));
        });
    }

    private void handleSpin() {
        game.getSpinButton().setOnAction(event -> {
            if (game.getBet() >= CasinoConfiguration.MIN_BET) {
                playerDAO.updateBalance(-game.getBet());
                transactionDAO.createTransaction(
                        new Transaction(0, GameManager.getInstance().getCurrentPlayer().getId(),
                                game.getBet(), Transaction.Status.APPROVED, Transaction.Type.BET)
                );
                game.setBalance(GameManager.getInstance().getCurrentPlayer().getBalance());
                for (Reel reel : game.getReels())
                    reel.replacement();
                game.getSpinButton().setDisable(true);
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
            final double stopingOffset = 0.25;
            //            final double deceleration = velocity / spinDuration;
            boolean[] stoppedReels = new boolean[game.getReels().size()];

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

                for (int nReel = 0; nReel < game.getReels().size(); nReel++) {
                    if (stoppedReels[nReel]) continue;
                    boolean timeStop = elapsedTime > spinDuration + nReel * stopingOffset;
                    stoppedReels[nReel] = game.getReels().get(nReel).reelSpin(deltaTime, velocity, timeStop);
                }

                boolean allStopped = true;
                for (boolean stopped : stoppedReels)
                    allStopped = allStopped && stopped;
                if (allStopped) {
                    checkWinner();
                    game.getSpinButton().setDisable(GameManager.getInstance().getCurrentPlayer().getBalance() < CasinoConfiguration.MIN_BET);
                    stop();
                }
            }
        };
        spinTimer.start();
    }

    public int checkWinner() {
        int winAmount = 0;
        List<int[]> winningLines = new ArrayList<>();

        for (int[] payline : game.getPaylines()) {
            int currentWin = 0;
            SymbolInfo startSymbol = game.getReels().getFirst().getWinningSymbols().get(payline[0]).getSymbolInfo();

            int connected = 1;
            for (int reel = 1; reel < game.getReels().size(); reel++) {
                SymbolInfo current = game.getReels().get(reel).getWinningSymbols().get(payline[reel]).getSymbolInfo();
                if (current.getId().equals(startSymbol.getId()))
                    connected++;
                else break;
            }

            if (connected >= 3) {
                currentWin += startSymbol.getPayouts()[connected - 1] * game.getBet();
                winningLines.add(payline);
                System.out.println(winAmount);
            }
            winAmount += currentWin;
        }

        if (winAmount > 0) {
            playerDAO.updateBalance(winAmount);
            game.setBalance(GameManager.getInstance().getCurrentPlayer().getBalance());
        }

        return winAmount;
    }

}

