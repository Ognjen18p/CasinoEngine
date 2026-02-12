package com.controller.pages;

import com.basis.pages.LoginPage;
import com.basis.security.LoginValidator;
import com.stylization.pages.LoginPageStylization;
import javafx.scene.Scene;
import main.GameManager;

public class LoginPageController extends PageController {

    private LoginPage loginPage;

    public LoginPageController(GameManager gameManager) {
        this.gameManager = gameManager;
        initializePage();
        setupEventHandlers();
    }

    @Override
    protected void initializePage() {
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

        if (!loginValidator.findUsername(username)) {
            loginPage.resetInputFields();
            loginPage.showErrorMessage(loginValidator.getErrorMessage());
            return;
        }

        if (!loginValidator.findPassword(password)) {
            loginPage.resetInputFields();
            loginPage.showErrorMessage(loginValidator.getErrorMessage());
            return;
        }

        loginPage.showErrorMessage("");
    }

    @Override
    public void showPage() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("Casino Engine - Login");
    }

    private void handleCreateNew() {
        gameManager.getRegistrationPageController().showPage();
    }
}