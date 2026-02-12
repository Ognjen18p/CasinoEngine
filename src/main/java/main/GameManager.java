package main;

import com.controller.pages.LoginPageController;
import com.controller.pages.RegistrationPageController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameManager {
    private final LoginPageController loginPageController;
    private final RegistrationPageController registrationPageController;
    private final Stage mainStage;

    public GameManager(){
        loginPageController = new LoginPageController(this);
        registrationPageController = new RegistrationPageController(this);
        mainStage = new Stage();
        initializeGameManager();
    }

    public LoginPageController getLoginPageController() {
        return loginPageController;
    }

    public RegistrationPageController getRegistrationPageController() {
        return registrationPageController;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainScene(Scene mainScene) {
        mainStage.setScene(mainScene);
    }

    public void initializeGameManager() {
        loginPageController.showPage();
        mainStage.show();
    }
}