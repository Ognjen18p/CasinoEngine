package com.manager;

import com.basis.page.Page;
import com.basis.person.Player;
import com.controller.Controller;
import com.controller.game.machine_game.SlotController;
import com.controller.game.table_game.BlackJackController;
import com.controller.game.table_game.RouletteController;
import com.controller.page.in_game_pages.CashierPageController;
import com.controller.page.LoginPageController;
import com.controller.page.in_game_pages.MenuPageController;
import com.controller.page.RegistrationPageController;
import com.controller.page.in_game_pages.PaymentPageController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameManager {
    private static GameManager instance;
    private Stage mainStage;
    private Player currentPlayer;
    private Controller currentController;
    private Controller previousController;

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
        controller.showScene();
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


//    private final LoginPageController loginPageController;
//    private final RegistrationPageController registrationPageController;
//    private final MenuPageController menuPageController;
//    private final CashierPageController cashierPageController;
//    private final PaymentPageController paymentPageController;
//
//    private Page previousPage;
//
//    private final BlackJackController blackJackController;
//    private final RouletteController rouletteController;
//    private final SlotController slotController;
//    private final Stage mainStage;
//
//    public GameManager(){
//        loginPageController = new LoginPageController(this);
//        registrationPageController = new RegistrationPageController(this);
//        menuPageController = new MenuPageController(this);
//        cashierPageController = new CashierPageController(this);
//        paymentPageController = new PaymentPageController(this);
//        blackJackController = new BlackJackController(this);
//        rouletteController = new RouletteController(this);
//        slotController = new SlotController(this);
//        mainStage = new Stage();
//        initializeGameManager();
//    }
//
//    public Page getPreviousPage() {
//        return previousPage;
//    }
//
//    public void setPreviousPage(Page previousPage) {
//        this.previousPage = previousPage;
//    }
//
//    public LoginPageController getLoginPageController() {
//        return loginPageController;
//    }
//
//    public RegistrationPageController getRegistrationPageController() {
//        return registrationPageController;
//    }
//
//    public MenuPageController getMenuPageController() {
//        return menuPageController;
//    }
//
//    public CashierPageController getCashierPageController() {
//        return cashierPageController;
//    }
//
//    public PaymentPageController getPaymentPageController() {
//        return paymentPageController;
//    }
//
//    public BlackJackController getBlackJackController() {
//        return blackJackController;
//    }
//
//    public RouletteController getRouletteController() {
//        return rouletteController;
//    }
//
//    public SlotController getSlotController() {
//        return slotController;
//    }
//
//    public Stage getMainStage() {
//        return mainStage;
//    }
//
//    public void setMainScene(Scene mainScene) {
//        mainStage.setScene(mainScene);
//    }
//
//    public void initializeGameManager() {
//        loginPageController.showScene();
//        mainStage.show();
//    }
}