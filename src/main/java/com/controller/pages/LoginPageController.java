package com.controller.pages;

import com.basis.pages.LoginPage;
import com.basis.person.Person;
import com.basis.security.LoginValidator;

///                                                                        ///
/// ----- Login Controller handles logic for Login Page functionality ---- ///
///                                                                        ///
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

        if (!LoginValidator.findUsername(username)) {
            loginPage.resetInputFields();
            loginPage.showErrorMessage(LoginValidator.getErrorMessage());
            return;
        }
        else if (!LoginValidator.findPassword(password)) {
            loginPage.resetInputFields();
            loginPage.showErrorMessage(LoginValidator.getErrorMessage());
            return;
        }
    }

    private void handleRegister() {

    }
}