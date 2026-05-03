package com.controller.page;

import com.application.configuration.GameSettings;
import com.basis.page.MenuPage;
import com.basis.person.Player;
import com.controller.Controller;
import com.controller.game.machine_game.SlotController;
import com.controller.game.table_game.BlackJackController;
import com.controller.game.table_game.RouletteController;
import com.stylization.page.MenuPageStylization;
import javafx.scene.Scene;
import com.application.GameManager;

public class MenuPageController extends Controller {
    private MenuPage menuPage;

    public MenuPageController() {
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void initializeScene() {
        menuPage = new MenuPage();
        new MenuPageStylization(menuPage);
        scene = new Scene(menuPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());
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
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Casino Engine - Main Menu");
    }

    private void handleLogout() {
        menuPage.showErrorMessage("Logging out...");

        GameManager.getInstance().setCurrentController(new LoginPageController());
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
        GameManager.getInstance().setCurrentController(new BlackJackController());
    }

    private void handleRouletteSelection() {
        GameManager.getInstance().setCurrentController(new RouletteController());
    }

    private void handleSlotsSelection() {
        GameManager.getInstance().setCurrentController(new SlotController());
    }

}
