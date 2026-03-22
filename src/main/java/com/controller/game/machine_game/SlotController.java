package com.controller.game.machine_game;

import com.basis.game.essentials.Vector2;
import com.basis.game.machine_game.Reel;
import com.basis.game.machine_game.Slot;
import com.controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import main.GameManager;

public class SlotController extends Controller {
    private Slot slot;
    private AnimationTimer spinTimer;

    public SlotController(GameManager gameManager) {
        this.gameManager = gameManager;
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        handleSpin();
        handleBetting();
    }

    @Override
    protected void initializeScene() {
        slot = new Slot(new Vector2(1000, 800), new Vector2(5, 4), 10, 1000);
        scene = new Scene(slot.getMainPane(), slot.getWindowSize().getX(), slot.getWindowSize().getY());
    }

    @Override
    public void showScene() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("SLOT");
    }

    private void handleBetting() {
        slot.getAddBetButton().setOnAction(event -> {
            slot.setBet(slot.getBet() + 10);
            slot.getSpinButton().setDisable(slot.getBet() < slot.getMinimumBet());
        });
        slot.getRemoveBetButton().setOnAction(event -> {
            slot.setBet(Math.max(slot.getMinimumBet(), slot.getBet() - 10));
        });
    }

    private void handleSpin() {
        slot.getSpinButton().setOnAction(event -> {
            if (slot.getBet() > slot.getMinimumBet()) {
                slot.setBalance(slot.getBalance() - slot.getBet());
                for (Reel reel : slot.getReels())
                    reel.replacement();
                slot.getSpinButton().setDisable(true);
                spin();
            }
        });
    }

    private void spin() {
        spinTimer = new AnimationTimer() {
            long previousTime = 0;
            long startTime = 0;
            double velocity = 2000;
            final double spinDuration = 3;
            final double deceleration = velocity / spinDuration;
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
                velocity -= deceleration * deltaTime;
                velocity = Math.max(500, velocity);
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
                    slot.checkWin();
                    slot.getSpinButton().setDisable(slot.getBalance() < slot.getMinimumBet());
                    stop();
                }
            }
        };
        spinTimer.start();
    }
}

