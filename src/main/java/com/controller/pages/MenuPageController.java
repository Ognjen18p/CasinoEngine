package com.controller.pages;

import com.basis.pages.MenuPage;
import com.basis.person.Player;
import com.controller.Controller;
import com.stylization.pages.MenuPageStylization;
import javafx.scene.Scene;
import main.GameManager;

public class MenuPageController extends Controller {
    private MenuPage menuPage;
    private Player currentPlayer;

    public MenuPageController(GameManager gameManager) {
        this.gameManager = gameManager;
        initializeScene();
        setupEventHandlers();
    }

    public MenuPageController(GameManager gameManager, Player player) {
        this.gameManager = gameManager;
        this.currentPlayer = player;
        initializeScene();
        setupEventHandlers();
        updatePlayerInfo();
    }

    @Override
    protected void initializeScene() {
        menuPage = new MenuPage();
        MenuPageStylization menuPageStylization = new MenuPageStylization(menuPage);
        scene = new Scene(menuPage.getMainLayout(), 1200, 800);
    }

    @Override
    protected void setupEventHandlers() {
        menuPage.getLogoutButton().setOnAction(event -> handleLogout());

        menuPage.getDepositButton().setOnAction(event -> handleDeposit());

        menuPage.getChatButton().setOnAction(event -> handleChat());

        menuPage.getGameListView().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleGameSelection();
            }
        });

        menuPage.getBlackjackImageButton().setOnAction(event -> handleBlackjackSelection());

        menuPage.getRouletteImageButton().setOnAction(event -> handleRouletteSelection());

        menuPage.getSlotImageButton().setOnAction(event -> handleSlotsSelection());
    }

    @Override
    public void showScene() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("Casino Engine - Main Menu");
    }

    private void handleLogout() {
        currentPlayer = null;

        menuPage.showErrorMessage("Logging out...");

        gameManager.getLoginPageController().showScene();
    }

    private void handleDeposit() {
        menuPage.showErrorMessage("Deposit feature coming soon!");
    }

    private void handleChat() {
        menuPage.showErrorMessage("Chat with Agent feature coming soon!");
    }

    private void handleGameSelection() {
        String selectedGame = menuPage.getGameListView().getSelectionModel().getSelectedItem();

        if (selectedGame == null) {
            return;
        }

        switch (selectedGame) {
            case "BlackJack":
                handleBlackjackSelection();
                break;
            case "Roulette":
                handleRouletteSelection();
                break;
            case "Slots":
                handleSlotsSelection();
                break;
            default:
                menuPage.showErrorMessage("Unknown game selected!");
        }
    }

    private void handleBlackjackSelection() {
        gameManager.getBlackJackController().showScene();
    }

    private void handleRouletteSelection() {
        gameManager.getRouletteController().showScene();
    }

    private void handleSlotsSelection() {
        menuPage.showErrorMessage("Slots coming soon!");
    }

    private void updatePlayerInfo() {
        if (currentPlayer != null) {
            menuPage.updateBalance(currentPlayer.getBalance());
        }
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        updatePlayerInfo();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
