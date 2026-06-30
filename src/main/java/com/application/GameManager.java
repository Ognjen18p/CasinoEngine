package com.application;

import com.basis.person.Player;
import com.controller.Controller;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameManager {
    private static GameManager instance;
    private Stage mainStage;
    private Player currentPlayer;

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void initializeElements(Stage stage) {
        this.mainStage = stage;
        mainStage.show();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void navigateTo(Controller controller) {
        controller.showScene();
    }

    public void setMainScene(Scene scene) {
        mainStage.setScene(scene);
    }

    public Stage getMainStage() {
        return mainStage;
    }
}