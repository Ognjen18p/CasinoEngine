package com.application;

import com.basis.person.Player;
import com.controller.Controller;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameManager {
    private static GameManager instance;
    private Stage mainStage;
    private Player currentPlayer;
    private Controller currentController;
    private Controller previousController;

    public GameManager() {
    }

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

    public void setCurrentController(Controller controller) {
        previousController = currentController;
        currentController = controller;
        currentController.showScene();
    }

    public void returnToPreviousController() {
        if (previousController != null)
            setCurrentController(previousController);
    }

    public void setMainScene(Scene scene) {
        mainStage.setScene(scene);
    }

    public Stage getMainStage() {
        return mainStage;
    }
}