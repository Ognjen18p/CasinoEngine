package main;

import com.controller.game.BlackJackController;
import com.controller.game.RouletteController;
import com.controller.pages.LoginPageController;
import com.controller.pages.MenuPageController;
import com.controller.pages.RegistrationPageController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameManager {
    private final LoginPageController loginPageController;
    private final RegistrationPageController registrationPageController;
    private final MenuPageController menuPageController;
    private final BlackJackController blackJackController;
    private final RouletteController rouletteController;
    private final Stage mainStage;

    public GameManager(){
        loginPageController = new LoginPageController(this);
        registrationPageController = new RegistrationPageController(this);
        menuPageController = new MenuPageController(this);
        blackJackController = new BlackJackController(this);
        rouletteController = new RouletteController(this);
        mainStage = new Stage();
        initializeGameManager();
    }

    public LoginPageController getLoginPageController() {
        return loginPageController;
    }

    public RegistrationPageController getRegistrationPageController() {
        return registrationPageController;
    }

    public MenuPageController getMenuPageController() {
        return menuPageController;
    }

    public BlackJackController getBlackJackController() {
        return blackJackController;
    }

    public RouletteController getRouletteController() {
        return rouletteController;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainScene(Scene mainScene) {
        mainStage.setScene(mainScene);
    }

    public void initializeGameManager() {
        loginPageController.showScene();
        mainStage.show();
    }
}