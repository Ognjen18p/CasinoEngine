package com.controller.game;

import com.basis.game.Game.ChipShop;
import com.basis.game.Roulette.Ball;
import com.basis.game.Roulette.Roulette;
import com.basis.game.Roulette.Vector2;
import com.controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import main.GameManager;

import java.util.Map;

public class RouletteController extends Controller {
    private Roulette roulette;
    private Ball ball;
    private ChipShop  chipShop;

    public RouletteController(GameManager gameManager) {
        this.gameManager = gameManager;
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        handleSpin();
    }

    @Override
    public void showScene() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("Roulette");
    }

    @Override
    protected void initializeScene() {
        roulette = new Roulette();
        ball = new Ball(roulette);
        chipShop = new ChipShop(roulette, roulette.getOwningChips());
        scene = new Scene(roulette.getMainPane(), 1000, 800);
    }

    private void handleSpin() {
        roulette.getSpinButton().setOnAction(event -> {
            ball.startBallMovement();
        });
    }


}
