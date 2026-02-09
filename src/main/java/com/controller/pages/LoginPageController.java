package com.controller.pages;

import com.basis.pages.LoginPage;
import com.basis.person.Person;
import com.basis.security.PasswordValidator;
import com.basis.security.UsernameValidator;

///                                                                     ///
/// ----- Login Controller handles logic for Login Page functions ---- ///
///                                                                    ///
public class LoginPageController {

    private LoginPage loginPage;
    private Person person;

    public LoginPageController(LoginPage loginPage) {
        this.loginPage = loginPage;
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        loginPage.getLoginButton().setOnAction(event -> handleLogin());

        loginPage.getRegisterButton().setOnAction(event -> handleRegister());

        loginPage.getPasswordField().setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = loginPage.getUsernameField().getText();
        String password = loginPage.getPasswordField().getText();

        if (!UsernameValidator.validateUsername(username)) {
            loginPage.showErrorMessage("Username not valid");
            loginPage.resetInputFields();
            return;
        }

        if (!PasswordValidator.isPasswordValid(password)) {
            loginPage.showErrorMessage("");
            loginPage.resetInputFields();
            return;
        } else {
            loginPage.showErrorMessage("Password not valid");
            loginPage.resetInputFields();
            return;
        }
    }

    /**
     * Handle register button click
     */
    private void handleRegister() {
        System.out.println("Navigating to Register page...");

        // TODO: Switch to Register page
        // SceneManager.getInstance().switchToRegisterPage();
    }
}