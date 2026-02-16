package com.controller.pages;

import com.basis.pages.LoginPage;
import com.basis.security.LoginValidator;
import com.controller.Controller;
import com.stylization.pages.LoginPageStylization;
import javafx.scene.Scene;
import main.GameManager;

public class LoginPageController extends Controller {

    private LoginPage loginPage;

    public LoginPageController(GameManager gameManager) {
        this.gameManager = gameManager;
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void initializeScene() {
        loginPage = new LoginPage();
        scene = new Scene(loginPage.getMainLayout(), 900, 700);
        LoginPageStylization loginPageStylization = new LoginPageStylization(loginPage);
    }

    @Override
    protected void setupEventHandlers() {
        loginPage.getLoginButton().setOnAction(event -> handleLogin());

        loginPage.getCreateAccountButton().setOnAction(event -> handleCreateNew());

        loginPage.getPasswordField().setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        LoginValidator loginValidator = new LoginValidator();
        String username = loginPage.getUsernameField().getText().trim();
        String password = loginPage.getPasswordField().getText();

        if (username.isEmpty() || password.isEmpty()) {
            loginPage.showErrorMessage("Username and password cannot be empty!");
            return;
        }

        loginPage.showErrorMessage("");
        loginPage.resetInputFields();

        gameManager.getMenuPageController().showScene();
    }

    @Override
    public void showScene() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("Casino Engine - Login");
    }

    private void handleCreateNew() {
        gameManager.getRegistrationPageController().showScene();
    }
}