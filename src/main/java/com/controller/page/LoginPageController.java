package com.controller.page;

import com.basis.game.essentials.GameSettings;
import com.basis.game.essentials.Vector2;
import com.basis.page.LoginPage;
import com.basis.security.LoginValidator;
import com.controller.Controller;
import com.controller.page.in_game_pages.MenuPageController;
import com.stylization.page.LoginPageStylization;
import javafx.scene.Scene;
import com.manager.GameManager;

public class LoginPageController extends Controller {

    private LoginPage loginPage;

    public LoginPageController() {
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void initializeScene() {
        loginPage = new LoginPage();

        scene = new Scene(loginPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(),  GameSettings.getInstance().getWindowHeight());

        LoginPageStylization loginPageStylization = new LoginPageStylization(loginPage);
    }

    @Override
    protected void setupEventHandlers() {
        handleLogin();
        handleCreateNew();
    }

    private void handleLogin() {
        loginPage.getLoginButton().setOnAction(event -> {
            LoginValidator loginValidator = new LoginValidator();
            String username = loginPage.getUsernameField().getText().trim();
            String password = loginPage.getPasswordField().getText();

            if (username.isEmpty() || password.isEmpty()) {
                loginPage.showErrorMessage("Username and password cannot be empty!");
                return;
            }

            loginPage.showErrorMessage("");
            loginPage.resetInputFields();

            GameManager.getInstance().setCurrentController(new MenuPageController());
        });
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Casino Engine - Login");
    }

    private void handleCreateNew() {
        GameManager.getInstance().setCurrentController(new RegistrationPageController());
    }
}